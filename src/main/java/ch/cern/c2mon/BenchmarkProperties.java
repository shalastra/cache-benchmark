package ch.cern.c2mon;

/**
 * @author Szymon Halastra
 */
public class BenchmarkProperties {

  public static final String IGNITE_PROVIDER = "org.apache.ignite.cache.CachingProvider";
  public static final String HAZELCAST_PROVIDER = "com.hazelcast.cache.HazelcastCachingProvider";
  public static final String EHCACHE_PROVIDER = "org.ehcache.jsr107.EhcacheCachingProvider";
  public static final String REDIS_PROVIDER = "org.redisson.jcache.JCachingProvider";

  public static final int WARM_UP_ITERATIONS = 1;
  public static final int MEASUREMENT_ITERATIONS = 1;

  public static final int FORKS_NUMBER = 1;

  public static final int CACHE_SIZE = 100000;
}
