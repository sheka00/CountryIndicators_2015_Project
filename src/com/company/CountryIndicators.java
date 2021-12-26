package com.company;

public class CountryIndicators {
    public final String countryName;
    public final String region;
    public final int happinessRank;
    public final float happinessScore;
    public final float standardError;
    public final float economy;
    public final float family;
    public final float health;
    public final float freedom;
    public final float trust;
    public final float generosity;
    public final float dystopiaResidual;

    public String getCountryName() {
        return countryName;
    }

    public String getRegion() {
        return region;
    }

    public int getHappinessRank() {
        return happinessRank;
    }

    public float getHappinessScore() {
        return happinessScore;
    }

    public float getStandardError() {
        return standardError;
    }

    public float getEconomy() {
        return economy;
    }

    public float getFamily() {
        return family;
    }

    public float getHealth() {
        return health;
    }

    public float getFreedom() {
        return freedom;
    }

    public float getTrust() {
        return trust;
    }

    public float getGenerosity() {
        return generosity;
    }

    public float getDystopiaResidual() {
        return dystopiaResidual;
    }

    CountryIndicators(String countryName, String region, int happinessRank, float happinessScore,
                      float standardError, float economy, float family, float health, float freedom,
                      float trust, float generosity, float dystopiaResidual) {
        this.countryName = countryName;
        this.region = region;
        this.happinessRank = happinessRank;
        this.happinessScore = happinessScore;
        this.standardError = standardError;
        this.economy = economy;
        this.family = family;
        this.health = health;
        this.freedom = freedom;
        this.trust = trust;
        this.generosity = generosity;
        this.dystopiaResidual = dystopiaResidual;
    }

    CountryIndicators(String[] indicators) {
        this.countryName = indicators[0];
        this.region = indicators[1];
        this.happinessRank = Integer.parseInt(indicators[2]);
        this.happinessScore = Float.parseFloat(indicators[3]);
        this.standardError = Float.parseFloat(indicators[4]);
        this.economy = Float.parseFloat(indicators[5]);
        this.family = Float.parseFloat(indicators[6]);
        this.health = Float.parseFloat(indicators[7]);
        this.freedom = Float.parseFloat(indicators[8]);
        this.trust = Float.parseFloat(indicators[9]);
        this.generosity = Float.parseFloat(indicators[10]);
        this.dystopiaResidual = Float.parseFloat(indicators[11]);
    }

    @Override
    public String toString() {
        return String.format("Название страны: %s | Регион: %s | Ранг по счастью: %d " +
                        "| Счастье: %.3f | Стандартная ошибка: %.5f | Экономика: %.5f " +
                        "| Семья: %.5f | Здоровье: %.5f | Свобода: %.5f | Доверие: %.5f " +
                        "| Щедрость: %.5f | Остаточная антиутопия: %.5f",
                this.countryName, this.region, this.happinessRank, this.happinessScore,
                this.standardError, this.economy, this.family, this.health,
                this.freedom, this.trust, this.generosity, this.dystopiaResidual
        );
    }
}