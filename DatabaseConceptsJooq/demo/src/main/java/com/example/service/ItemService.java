package com.example.service;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jooq.tables.records.ItemsRecord;

import lombok.extern.slf4j.Slf4j;

import static com.example.jooq.tables.Items.ITEMS;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Service that uses jOOQ's DSLContext to interact with the items table.
 *
 * The DSLContext bean is auto-configured by spring-boot-starter-jooq using
 * the DataSource defined in application.properties.
 */
@Service
@Slf4j
public class ItemService {

    private final DSLContext dsl;

    public ItemService(DSLContext dsl) {
        this.dsl = dsl;
    }

    /**
     * Immutable value object representing a single item to insert.
     */
    public record ItemInput(
            String name,
            String description,
            BigDecimal price,
            int quantity) {
    }

    public List<ItemInput> generateInputData(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> new ItemInput(
                        "Item " + i,
                        "Description for item " + i,
                        new BigDecimal("9.99").add(new BigDecimal(i)),
                        100 + i))
                .toList();
    }

    /*
     * A single SQL statement in Postgres is already atomic.
     * If it succeeds, it commits.
     * If it fails, it rolls back automatically.
     * 
     * So for a simple “wipe the table” operation, no explicit transaction is
     * required.
     */
    public void clearDB() {
        dsl.deleteFrom(ITEMS).execute();
        log.info("Cleared items table");
    }

    public int getItemTableSize() {
        int count = dsl.selectCount().from(ITEMS).fetchOneInto(Integer.class);
        log.info("Current items table size: {}", count);
        return count;
    }

    /**
     * Batch-inserts a list of items into the items table.
     * 
     * Using setters like:
     * ItemsRecord r = dsl.newRecord(ITEMS);
     * r.setName(item.name());
     * ...
     * 
     * Instead of .values(), as that requires all columns including id, created_at,
     * updated_at which you don't want to set manually
     *
     * @param items list of items to insert
     * @return array of affected row counts, one per statement
     */
    @Transactional
    public int[] batchInsert(List<ItemInput> items) {
        List<ItemsRecord> records = items.stream()
                .map(item -> {
                    ItemsRecord r = dsl.newRecord(ITEMS);
                    r.setName(item.name());
                    r.setDescription(item.description());
                    r.setPrice(item.price());
                    r.setQuantity(item.quantity());
                    return r;
                })
                .toList();

        return dsl.batchInsert(records).execute();
    }

}
