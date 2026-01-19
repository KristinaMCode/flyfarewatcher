package com.ffw.flyfarewatcher.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RyanairApiServiceTest {

        @Test
        void shouldParsePriceCorrectly() throws Exception {
            String fakeJson = """
                    {
                      "trips": [
                        {
                          "dates": [
                            {
                              "flights": [
                                {
                                  "regularFare": {
                                    "fares": [
                                      {
                                        "amount": 61.99
                                      }
                                    ]
                                  }
                                }
                              ]
                            }
                          ]
                        }
                      ]
                    }
                    """;
            RyanairApiService service = new RyanairApiService();
            double price = service.parsePrice(fakeJson);
            assertEquals(61.99, price);
        }

        @Test
    void shouldReturnZeroIfPriceMissing() throws  Exception{
            String fakeJson = """
    {
      "trips": [
        {
          "dates": [
            {
              "flights": [
                {
                  "regularFare": {
                    "fares": []
                  }
                }
              ]
            }
          ]
        }
      ]
    }
    """;

            RyanairApiService ryanairApiService = new RyanairApiService();
            double price = ryanairApiService.parsePrice(fakeJson);
            assertEquals(0.0,price);
        }
    }
