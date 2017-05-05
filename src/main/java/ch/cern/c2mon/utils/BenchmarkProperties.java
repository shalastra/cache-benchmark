package ch.cern.c2mon.utils;

import javax.cache.configuration.MutableConfiguration;

import ch.cern.c2mon.entities.Entity;

/**
 * @author Szymon Halastra
 */
public class BenchmarkProperties {

  public static final int FORKS_NUMBER = 5;
  public static final int CACHE_SIZE = 10000;

  public static MutableConfiguration<Long, Entity> createMutableConfiguration() {
    MutableConfiguration<Long, Entity> configuration = new MutableConfiguration<>();

    configuration.setStoreByValue(true); // otherwise value has to be Serializable
    configuration.setTypes(Long.class, Entity.class);

    return configuration;
  }
}
