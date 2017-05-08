package ch.cern.c2mon.utils;

import java.io.Serializable;
import java.util.*;

import javax.cache.Cache;
import javax.cache.configuration.MutableConfiguration;

import ch.cern.c2mon.entities.Entity;

/**
 * @author Szymon Halastra
 */
public class BenchmarkProperties {

  public static final int WARM_UP_ITERATIONS = 2;
  public static final int MEASUREMENT_ITERATIONS = 2;

  public static final int FORKS_NUMBER = 5;
  public static final int CACHE_SIZE = 10000;

  public static MutableConfiguration<Long, Entity> createMutableConfiguration() {
    MutableConfiguration<Long, Entity> configuration = new MutableConfiguration<>();

    configuration.setStoreByValue(true); // otherwise value has to be Serializable
    configuration.setTypes(Long.class, Entity.class);
    configuration.setStatisticsEnabled(true);

    return configuration;
  }

  public static Long getRandomKey(Cache<Long, Entity> cache) {
    List<Long> keys = new ArrayList<>(getKeys(cache));

    Collections.shuffle(keys);

    return keys.get(0);
  }

  public static Set<Long> getKeys(Cache<Long, Entity> cache) {
    Set<Long> keys = new HashSet<>();

    Iterator<Cache.Entry<Long, Entity>> entries = cache.iterator();
    while (entries.hasNext()) {
      Cache.Entry<Long, Entity> entry = entries.next();
      keys.add(entry.getKey());
    }

    return keys;
  }

  public static Map<Long, Entity> createEntities() {
    Map<Long, Entity> entityMap = new HashMap<>();

    for (int i = 0; i < CACHE_SIZE; i++) {
      Entity entity = new Entity();
      entityMap.put(entity.getId(), entity);
    }

    return entityMap;
  }
}
