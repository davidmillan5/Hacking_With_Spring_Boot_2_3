package com.greglturnquist.hackingspringboot.reactive.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void itemBasicShouldWork() {

        Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);



        // Test various aspects using AssertJ
        assertThat(sampleItem.getId()).isEqualTo("item1");
        assertThat(sampleItem.getName()).isEqualTo("TV tray");
        assertThat(sampleItem.getDescription()).isEqualTo("Alf TV tray");
        assertThat(sampleItem.getPrice()).isEqualTo(19.99);

        // Verify toString

        assertThat(sampleItem.toString()).isEqualTo( //
                "Item{id='item1', name='TV tray', description='Alf TV tray', price=19.99}");

        Item sampleItem2 = new Item("item1", "TV tray", "Alf TV tray", 19.99);
        assertThat(sampleItem).isEqualTo(sampleItem2);


        // Test various Setters
        sampleItem.setPrice(21.99);
        assertThat(sampleItem.getPrice()).isEqualTo(21.99);
        sampleItem.setId("item2");
        assertThat(sampleItem.getId()).isEqualTo("item2");
        sampleItem.setName("Sony Bravia");
        assertThat(sampleItem.getName()).isEqualTo("Sony Bravia");
        sampleItem.setDescription("Smart TV, Dolby Atmos");
        assertThat(sampleItem.getDescription()).isEqualTo("Smart TV, Dolby Atmos");


    }

}