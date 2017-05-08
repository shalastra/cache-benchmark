package ch.cern.c2mon.utils;

import java.util.Map;

import ch.cern.c2mon.entities.Entity;

/**
 * @author Szymon Halastra
 */
public interface BenchmarkedMethods {

  void putEntity();

  Entity getEntity();

  Entity getAndPutEntity();

  Map<Long, Entity> getAllEntities();

  void putAllEntities();

  void putIfAbsentEntity();

  void removeEntity();

  Entity getAndRemoveEntity();

  void replaceEntity();

  void getAndReplaceEntity();

  void removeAllEntities();
}
