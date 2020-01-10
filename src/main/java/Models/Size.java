package Models;

import Utils.Utils;

public class Size {

    private double size;

    private Scale scale;

    public static final int MAX_VALUE = 1024;

    public static final double MIN_VALUE = 0.1;

    private enum Scale {
        BYTES("B"), KILOBYTES("KB"), MEGABYTES("MB"), GIGABYTES("GB");
        private String value;

        private Scale(String scale) {
            this.value = scale;
        }

        public String getValue() {
            return this.value;
        }
    }

    public Size(double size) {
        double kilobytes = Utils.round(size / MAX_VALUE, 2);
        double megabytes = Utils.round(kilobytes / MAX_VALUE, 2);
        double gigabytes = Utils.round(megabytes / MAX_VALUE, 2);

        if (gigabytes > MIN_VALUE) {
            this.size = gigabytes;
            this.scale = Scale.GIGABYTES;
        } else if (megabytes > MIN_VALUE) {
            this.size = megabytes;
            this.scale = Scale.MEGABYTES;
        } else if (kilobytes > MIN_VALUE) {
            this.size = kilobytes;
            this.scale = Scale.KILOBYTES;
        } else {
            this.size = size;
            this.scale = Scale.BYTES;
        }

    }

    public String toString() {
        return size + "" + scale.getValue();
    }
}
