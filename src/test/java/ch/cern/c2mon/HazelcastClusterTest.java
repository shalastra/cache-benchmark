package ch.cern.c2mon;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Szymon Halastra
 */

public class HazelcastClusterTest {

  @Test
  public void test() {
    HazelcastInstance h1 = Hazelcast.newHazelcastInstance();
    HazelcastInstance h2 = Hazelcast.newHazelcastInstance();

    Map map1 = h1.getMap("testmap");
    for(int i = 0; i<10000; i++) {
      map1.put(i, "value " + i);
    }

    assertEquals(10000, map1.size());
  }

  @After
  public void cleanUp() throws Exception {
    Hazelcast.shutdownAll();
  }
}
