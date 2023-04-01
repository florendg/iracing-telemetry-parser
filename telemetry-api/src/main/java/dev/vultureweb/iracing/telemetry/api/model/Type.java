package dev.vultureweb.iracing.telemetry.api.model;

public enum Type {
   BOOLEAN(1),
   INTEGER(2),
   BITFIELD(3),
   FLOAT(4),
   DOUBLE(5);

   private final int code;

   Type(int code) {
      this.code = code;
   }

   public static Type fromCode(int code) {
      for (Type value : values()) {
         if (code == value.getCode()) {
            return value;
         }
      }
      throw new IllegalArgumentException("Unknown code");
   }

   public int getCode() {
      return this.code;
   }

}
