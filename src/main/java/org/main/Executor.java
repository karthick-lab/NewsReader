package org.main;

import org.goldpriceandstocknews.Goldpricereader;

import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws InterruptedException, IOException {

        Goldpricereader.getgoldprice();

    }
}
