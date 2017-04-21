package ch.cern.c2mon.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import lombok.Data;

/**
 * @author Szymon Halastra
 */

@Data
public class Entity {

  private long id;

  private String name;
  private String description;
  private String dataType;

  private short mode;

  private boolean isInOperation;
  private boolean isInMaintenance;
  private boolean isInTest;
  private boolean isInUnconfigured;

  private Object value;

  private String valueDescription;
  private String unit;

  private Map<String, String> metadata;

  private boolean isValid;
  private boolean isExistingTag;
  private boolean isSimulated;

  private Timestamp timestamp;

  private boolean isLogged;

  private Random random = new Random();

  private static final Class[] classes = {String.class, Long.class, Integer.class};
  private static final String[] supervisors = {"James", "Bob", "Alice", "Sara", "Larry", "Kate"};

  public Entity() {
    this.id = ThreadLocalRandom.current().nextLong(100000, 999999);
    this.name = "tag_" + random.nextInt();
    this.description = this.name + " description";
    this.dataType = "tag";
    this.mode = (short) ThreadLocalRandom.current().nextInt(0, 3);
    this.isInOperation = random.nextBoolean();
    this.isInMaintenance = random.nextBoolean();
    this.isInTest = random.nextBoolean();
    this.isInUnconfigured = random.nextBoolean();
    this.value = classes[random.nextInt(classes.length)];
    this.valueDescription = this.value.toString();
    this.unit = this.value.toString();
    this.metadata = new HashMap<String, String>() {{
      put("author", supervisors[random.nextInt(supervisors.length)]);
    }};
    this.isValid = random.nextBoolean();
    this.isExistingTag = random.nextBoolean();
    this.isSimulated = random.nextBoolean();
    this.timestamp = new Timestamp(new Date().getTime());
    this.isLogged = random.nextBoolean();
  }
}
