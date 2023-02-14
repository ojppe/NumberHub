/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.data.units.collections

import com.sadellie.unitto.data.units.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.unitgroups.UnitGroup
import java.math.BigDecimal

internal val pressureCollection: List<AbstractUnit> by lazy {
    listOf(
        MyUnit(MyUnitIDS.attopascal,            BigDecimal.valueOf(1),                      UnitGroup.PRESSURE, R.string.attopascal,            R.string.attopascal_short),
        MyUnit(MyUnitIDS.femtopascal,           BigDecimal.valueOf(1E+3),                   UnitGroup.PRESSURE, R.string.femtopascal,           R.string.femtopascal_short),
        MyUnit(MyUnitIDS.picopascal,            BigDecimal.valueOf(1E+6),                   UnitGroup.PRESSURE, R.string.picopascal,            R.string.picopascal_short),
        MyUnit(MyUnitIDS.nanopascal,            BigDecimal.valueOf(1E+9),                   UnitGroup.PRESSURE, R.string.nanopascal,            R.string.nanopascal_short),
        MyUnit(MyUnitIDS.micropascal,           BigDecimal.valueOf(1E+12),                  UnitGroup.PRESSURE, R.string.micropascal,           R.string.micropascal_short),
        MyUnit(MyUnitIDS.millipascal,           BigDecimal.valueOf(1E+15),                  UnitGroup.PRESSURE, R.string.millipascal,           R.string.millipascal_short),
        MyUnit(MyUnitIDS.centipascal,           BigDecimal.valueOf(1E+16),                  UnitGroup.PRESSURE, R.string.centipascal,           R.string.centipascal_short),
        MyUnit(MyUnitIDS.decipascal,            BigDecimal.valueOf(1E+17),                  UnitGroup.PRESSURE, R.string.decipascal,            R.string.decipascal_short),
        MyUnit(MyUnitIDS.pascal,                BigDecimal.valueOf(1E+18),                  UnitGroup.PRESSURE, R.string.pascal,                R.string.pascal_short),
        MyUnit(MyUnitIDS.dekapascal,            BigDecimal.valueOf(1E+19),                  UnitGroup.PRESSURE, R.string.dekapascal,            R.string.dekapascal_short),
        MyUnit(MyUnitIDS.hectopascal,           BigDecimal.valueOf(1E+20),                  UnitGroup.PRESSURE, R.string.hectopascal,           R.string.hectopascal_short),
        MyUnit(MyUnitIDS.millibar,              BigDecimal.valueOf(1E+20),                  UnitGroup.PRESSURE, R.string.millibar,              R.string.millibar_short),
        MyUnit(MyUnitIDS.bar,                   BigDecimal.valueOf(1E+23),                  UnitGroup.PRESSURE, R.string.bar,                   R.string.bar_short),
        MyUnit(MyUnitIDS.kilopascal,            BigDecimal.valueOf(1E+21),                  UnitGroup.PRESSURE, R.string.kilopascal,            R.string.kilopascal_short),
        MyUnit(MyUnitIDS.megapascal,            BigDecimal.valueOf(1E+24),                  UnitGroup.PRESSURE, R.string.megapascal,            R.string.megapascal_short),
        MyUnit(MyUnitIDS.gigapascal,            BigDecimal.valueOf(1E+27),                  UnitGroup.PRESSURE, R.string.gigapascal,            R.string.gigapascal_short),
        MyUnit(MyUnitIDS.terapascal,            BigDecimal.valueOf(1E+30),                  UnitGroup.PRESSURE, R.string.terapascal,            R.string.terapascal_short),
        MyUnit(MyUnitIDS.petapascal,            BigDecimal.valueOf(1E+33),                  UnitGroup.PRESSURE, R.string.petapascal,            R.string.petapascal_short),
        MyUnit(MyUnitIDS.exapascal,             BigDecimal.valueOf(1E+36),                  UnitGroup.PRESSURE, R.string.exapascal,             R.string.exapascal_short),
        MyUnit(MyUnitIDS.psi,                   BigDecimal.valueOf(6.8947572931783E+21),    UnitGroup.PRESSURE, R.string.psi,                   R.string.psi_short),
        MyUnit(MyUnitIDS.ksi,                   BigDecimal.valueOf(6.8947572931783E+24),    UnitGroup.PRESSURE, R.string.ksi,                   R.string.ksi_short),
        MyUnit(MyUnitIDS.standard_atmosphere,   BigDecimal.valueOf(101.325E+21),            UnitGroup.PRESSURE, R.string.standard_atmosphere,   R.string.standard_atmosphere_short),
        MyUnit(MyUnitIDS.torr,                  BigDecimal.valueOf(1.3332236842108281E+20), UnitGroup.PRESSURE, R.string.torr,                  R.string.torr_short),
        MyUnit(MyUnitIDS.micron_of_mercury,     BigDecimal.valueOf(1.3332236842108281E+17), UnitGroup.PRESSURE, R.string.micron_of_mercury,     R.string.micron_of_mercury_short),
        MyUnit(MyUnitIDS.millimeter_of_mercury, BigDecimal.valueOf(1.3332236842108281E+20), UnitGroup.PRESSURE, R.string.millimeter_of_mercury, R.string.millimeter_of_mercury_short),
    )
}