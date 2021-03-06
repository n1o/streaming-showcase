/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.mbarak.showcase;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ExtracedFeatures extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -151421535334912014L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ExtracedFeatures\",\"namespace\":\"io.mbarak.showcase\",\"fields\":[{\"name\":\"userId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"feature1\",\"type\":\"double\"},{\"name\":\"feature3\",\"type\":\"double\"},{\"name\":\"timestamp\",\"type\":\"long\"},{\"name\":\"good\",\"type\":\"int\"},{\"name\":\"neutral\",\"type\":\"int\"},{\"name\":\"bad\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<ExtracedFeatures> ENCODER =
      new BinaryMessageEncoder<ExtracedFeatures>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<ExtracedFeatures> DECODER =
      new BinaryMessageDecoder<ExtracedFeatures>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<ExtracedFeatures> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<ExtracedFeatures> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<ExtracedFeatures>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this ExtracedFeatures to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a ExtracedFeatures from a ByteBuffer. */
  public static ExtracedFeatures fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String userId;
  @Deprecated public double feature1;
  @Deprecated public double feature3;
  @Deprecated public long timestamp;
  @Deprecated public int good;
  @Deprecated public int neutral;
  @Deprecated public int bad;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public ExtracedFeatures() {}

  /**
   * All-args constructor.
   * @param userId The new value for userId
   * @param feature1 The new value for feature1
   * @param feature3 The new value for feature3
   * @param timestamp The new value for timestamp
   * @param good The new value for good
   * @param neutral The new value for neutral
   * @param bad The new value for bad
   */
  public ExtracedFeatures(java.lang.String userId, java.lang.Double feature1, java.lang.Double feature3, java.lang.Long timestamp, java.lang.Integer good, java.lang.Integer neutral, java.lang.Integer bad) {
    this.userId = userId;
    this.feature1 = feature1;
    this.feature3 = feature3;
    this.timestamp = timestamp;
    this.good = good;
    this.neutral = neutral;
    this.bad = bad;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return userId;
    case 1: return feature1;
    case 2: return feature3;
    case 3: return timestamp;
    case 4: return good;
    case 5: return neutral;
    case 6: return bad;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: userId = (java.lang.String)value$; break;
    case 1: feature1 = (java.lang.Double)value$; break;
    case 2: feature3 = (java.lang.Double)value$; break;
    case 3: timestamp = (java.lang.Long)value$; break;
    case 4: good = (java.lang.Integer)value$; break;
    case 5: neutral = (java.lang.Integer)value$; break;
    case 6: bad = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'userId' field.
   * @return The value of the 'userId' field.
   */
  public java.lang.String getUserId() {
    return userId;
  }

  /**
   * Sets the value of the 'userId' field.
   * @param value the value to set.
   */
  public void setUserId(java.lang.String value) {
    this.userId = value;
  }

  /**
   * Gets the value of the 'feature1' field.
   * @return The value of the 'feature1' field.
   */
  public java.lang.Double getFeature1() {
    return feature1;
  }

  /**
   * Sets the value of the 'feature1' field.
   * @param value the value to set.
   */
  public void setFeature1(java.lang.Double value) {
    this.feature1 = value;
  }

  /**
   * Gets the value of the 'feature3' field.
   * @return The value of the 'feature3' field.
   */
  public java.lang.Double getFeature3() {
    return feature3;
  }

  /**
   * Sets the value of the 'feature3' field.
   * @param value the value to set.
   */
  public void setFeature3(java.lang.Double value) {
    this.feature3 = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return The value of the 'timestamp' field.
   */
  public java.lang.Long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(java.lang.Long value) {
    this.timestamp = value;
  }

  /**
   * Gets the value of the 'good' field.
   * @return The value of the 'good' field.
   */
  public java.lang.Integer getGood() {
    return good;
  }

  /**
   * Sets the value of the 'good' field.
   * @param value the value to set.
   */
  public void setGood(java.lang.Integer value) {
    this.good = value;
  }

  /**
   * Gets the value of the 'neutral' field.
   * @return The value of the 'neutral' field.
   */
  public java.lang.Integer getNeutral() {
    return neutral;
  }

  /**
   * Sets the value of the 'neutral' field.
   * @param value the value to set.
   */
  public void setNeutral(java.lang.Integer value) {
    this.neutral = value;
  }

  /**
   * Gets the value of the 'bad' field.
   * @return The value of the 'bad' field.
   */
  public java.lang.Integer getBad() {
    return bad;
  }

  /**
   * Sets the value of the 'bad' field.
   * @param value the value to set.
   */
  public void setBad(java.lang.Integer value) {
    this.bad = value;
  }

  /**
   * Creates a new ExtracedFeatures RecordBuilder.
   * @return A new ExtracedFeatures RecordBuilder
   */
  public static io.mbarak.showcase.ExtracedFeatures.Builder newBuilder() {
    return new io.mbarak.showcase.ExtracedFeatures.Builder();
  }

  /**
   * Creates a new ExtracedFeatures RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new ExtracedFeatures RecordBuilder
   */
  public static io.mbarak.showcase.ExtracedFeatures.Builder newBuilder(io.mbarak.showcase.ExtracedFeatures.Builder other) {
    return new io.mbarak.showcase.ExtracedFeatures.Builder(other);
  }

  /**
   * Creates a new ExtracedFeatures RecordBuilder by copying an existing ExtracedFeatures instance.
   * @param other The existing instance to copy.
   * @return A new ExtracedFeatures RecordBuilder
   */
  public static io.mbarak.showcase.ExtracedFeatures.Builder newBuilder(io.mbarak.showcase.ExtracedFeatures other) {
    return new io.mbarak.showcase.ExtracedFeatures.Builder(other);
  }

  /**
   * RecordBuilder for ExtracedFeatures instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ExtracedFeatures>
    implements org.apache.avro.data.RecordBuilder<ExtracedFeatures> {

    private java.lang.String userId;
    private double feature1;
    private double feature3;
    private long timestamp;
    private int good;
    private int neutral;
    private int bad;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.mbarak.showcase.ExtracedFeatures.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.userId)) {
        this.userId = data().deepCopy(fields()[0].schema(), other.userId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.feature1)) {
        this.feature1 = data().deepCopy(fields()[1].schema(), other.feature1);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.feature3)) {
        this.feature3 = data().deepCopy(fields()[2].schema(), other.feature3);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[3].schema(), other.timestamp);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.good)) {
        this.good = data().deepCopy(fields()[4].schema(), other.good);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.neutral)) {
        this.neutral = data().deepCopy(fields()[5].schema(), other.neutral);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.bad)) {
        this.bad = data().deepCopy(fields()[6].schema(), other.bad);
        fieldSetFlags()[6] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing ExtracedFeatures instance
     * @param other The existing instance to copy.
     */
    private Builder(io.mbarak.showcase.ExtracedFeatures other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.userId)) {
        this.userId = data().deepCopy(fields()[0].schema(), other.userId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.feature1)) {
        this.feature1 = data().deepCopy(fields()[1].schema(), other.feature1);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.feature3)) {
        this.feature3 = data().deepCopy(fields()[2].schema(), other.feature3);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[3].schema(), other.timestamp);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.good)) {
        this.good = data().deepCopy(fields()[4].schema(), other.good);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.neutral)) {
        this.neutral = data().deepCopy(fields()[5].schema(), other.neutral);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.bad)) {
        this.bad = data().deepCopy(fields()[6].schema(), other.bad);
        fieldSetFlags()[6] = true;
      }
    }

    /**
      * Gets the value of the 'userId' field.
      * @return The value.
      */
    public java.lang.String getUserId() {
      return userId;
    }

    /**
      * Sets the value of the 'userId' field.
      * @param value The value of 'userId'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setUserId(java.lang.String value) {
      validate(fields()[0], value);
      this.userId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'userId' field has been set.
      * @return True if the 'userId' field has been set, false otherwise.
      */
    public boolean hasUserId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'userId' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearUserId() {
      userId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'feature1' field.
      * @return The value.
      */
    public java.lang.Double getFeature1() {
      return feature1;
    }

    /**
      * Sets the value of the 'feature1' field.
      * @param value The value of 'feature1'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setFeature1(double value) {
      validate(fields()[1], value);
      this.feature1 = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'feature1' field has been set.
      * @return True if the 'feature1' field has been set, false otherwise.
      */
    public boolean hasFeature1() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'feature1' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearFeature1() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'feature3' field.
      * @return The value.
      */
    public java.lang.Double getFeature3() {
      return feature3;
    }

    /**
      * Sets the value of the 'feature3' field.
      * @param value The value of 'feature3'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setFeature3(double value) {
      validate(fields()[2], value);
      this.feature3 = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'feature3' field has been set.
      * @return True if the 'feature3' field has been set, false otherwise.
      */
    public boolean hasFeature3() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'feature3' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearFeature3() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * @return The value.
      */
    public java.lang.Long getTimestamp() {
      return timestamp;
    }

    /**
      * Sets the value of the 'timestamp' field.
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setTimestamp(long value) {
      validate(fields()[3], value);
      this.timestamp = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearTimestamp() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'good' field.
      * @return The value.
      */
    public java.lang.Integer getGood() {
      return good;
    }

    /**
      * Sets the value of the 'good' field.
      * @param value The value of 'good'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setGood(int value) {
      validate(fields()[4], value);
      this.good = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'good' field has been set.
      * @return True if the 'good' field has been set, false otherwise.
      */
    public boolean hasGood() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'good' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearGood() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'neutral' field.
      * @return The value.
      */
    public java.lang.Integer getNeutral() {
      return neutral;
    }

    /**
      * Sets the value of the 'neutral' field.
      * @param value The value of 'neutral'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setNeutral(int value) {
      validate(fields()[5], value);
      this.neutral = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'neutral' field has been set.
      * @return True if the 'neutral' field has been set, false otherwise.
      */
    public boolean hasNeutral() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'neutral' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearNeutral() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'bad' field.
      * @return The value.
      */
    public java.lang.Integer getBad() {
      return bad;
    }

    /**
      * Sets the value of the 'bad' field.
      * @param value The value of 'bad'.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder setBad(int value) {
      validate(fields()[6], value);
      this.bad = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'bad' field has been set.
      * @return True if the 'bad' field has been set, false otherwise.
      */
    public boolean hasBad() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'bad' field.
      * @return This builder.
      */
    public io.mbarak.showcase.ExtracedFeatures.Builder clearBad() {
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ExtracedFeatures build() {
      try {
        ExtracedFeatures record = new ExtracedFeatures();
        record.userId = fieldSetFlags()[0] ? this.userId : (java.lang.String) defaultValue(fields()[0]);
        record.feature1 = fieldSetFlags()[1] ? this.feature1 : (java.lang.Double) defaultValue(fields()[1]);
        record.feature3 = fieldSetFlags()[2] ? this.feature3 : (java.lang.Double) defaultValue(fields()[2]);
        record.timestamp = fieldSetFlags()[3] ? this.timestamp : (java.lang.Long) defaultValue(fields()[3]);
        record.good = fieldSetFlags()[4] ? this.good : (java.lang.Integer) defaultValue(fields()[4]);
        record.neutral = fieldSetFlags()[5] ? this.neutral : (java.lang.Integer) defaultValue(fields()[5]);
        record.bad = fieldSetFlags()[6] ? this.bad : (java.lang.Integer) defaultValue(fields()[6]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<ExtracedFeatures>
    WRITER$ = (org.apache.avro.io.DatumWriter<ExtracedFeatures>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<ExtracedFeatures>
    READER$ = (org.apache.avro.io.DatumReader<ExtracedFeatures>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
