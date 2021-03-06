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
public class UserKey extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2982911864828158245L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"UserKey\",\"namespace\":\"io.mbarak.showcase\",\"fields\":[{\"name\":\"user\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<UserKey> ENCODER =
      new BinaryMessageEncoder<UserKey>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<UserKey> DECODER =
      new BinaryMessageDecoder<UserKey>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<UserKey> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<UserKey> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<UserKey>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this UserKey to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a UserKey from a ByteBuffer. */
  public static UserKey fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String user;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public UserKey() {}

  /**
   * All-args constructor.
   * @param user The new value for user
   */
  public UserKey(java.lang.String user) {
    this.user = user;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return user;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: user = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'user' field.
   * @return The value of the 'user' field.
   */
  public java.lang.String getUser() {
    return user;
  }

  /**
   * Sets the value of the 'user' field.
   * @param value the value to set.
   */
  public void setUser(java.lang.String value) {
    this.user = value;
  }

  /**
   * Creates a new UserKey RecordBuilder.
   * @return A new UserKey RecordBuilder
   */
  public static io.mbarak.showcase.UserKey.Builder newBuilder() {
    return new io.mbarak.showcase.UserKey.Builder();
  }

  /**
   * Creates a new UserKey RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new UserKey RecordBuilder
   */
  public static io.mbarak.showcase.UserKey.Builder newBuilder(io.mbarak.showcase.UserKey.Builder other) {
    return new io.mbarak.showcase.UserKey.Builder(other);
  }

  /**
   * Creates a new UserKey RecordBuilder by copying an existing UserKey instance.
   * @param other The existing instance to copy.
   * @return A new UserKey RecordBuilder
   */
  public static io.mbarak.showcase.UserKey.Builder newBuilder(io.mbarak.showcase.UserKey other) {
    return new io.mbarak.showcase.UserKey.Builder(other);
  }

  /**
   * RecordBuilder for UserKey instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<UserKey>
    implements org.apache.avro.data.RecordBuilder<UserKey> {

    private java.lang.String user;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.mbarak.showcase.UserKey.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.user)) {
        this.user = data().deepCopy(fields()[0].schema(), other.user);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing UserKey instance
     * @param other The existing instance to copy.
     */
    private Builder(io.mbarak.showcase.UserKey other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.user)) {
        this.user = data().deepCopy(fields()[0].schema(), other.user);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'user' field.
      * @return The value.
      */
    public java.lang.String getUser() {
      return user;
    }

    /**
      * Sets the value of the 'user' field.
      * @param value The value of 'user'.
      * @return This builder.
      */
    public io.mbarak.showcase.UserKey.Builder setUser(java.lang.String value) {
      validate(fields()[0], value);
      this.user = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'user' field has been set.
      * @return True if the 'user' field has been set, false otherwise.
      */
    public boolean hasUser() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'user' field.
      * @return This builder.
      */
    public io.mbarak.showcase.UserKey.Builder clearUser() {
      user = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserKey build() {
      try {
        UserKey record = new UserKey();
        record.user = fieldSetFlags()[0] ? this.user : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<UserKey>
    WRITER$ = (org.apache.avro.io.DatumWriter<UserKey>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<UserKey>
    READER$ = (org.apache.avro.io.DatumReader<UserKey>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
