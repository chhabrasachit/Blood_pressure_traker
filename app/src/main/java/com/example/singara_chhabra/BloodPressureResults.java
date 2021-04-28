package com.example.singara_chhabra;

public class BloodPressureResults {

    public static final String mCategories[] = {
            "Normal BP",
            "Elevated BP",
            "High BP (Stage 1)",
            "High BP (Stage 2)",
            "Hypertensive Crisis"
    };

    public static int bp(int systolic, int diastolic) {

        if (systolic < 120 && diastolic < 80)
            return 0;   // Normal BP
        else if (systolic < 130 && diastolic < 80)
            return 1;   // Elevated BP
        else if (systolic < 140 && diastolic < 90)
            return 2;   // Stage 1
        else if (systolic < 180 && diastolic < 120)
            return 3;   // Stage 2
        else
            return 4;
    }

}
