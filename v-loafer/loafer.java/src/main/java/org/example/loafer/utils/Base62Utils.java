package org.example.loafer.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.Hashing;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@UtilityClass
public class Base62Utils {

    private static final int BASE = 62;

    private static final Map<Integer, Character> base62Map = ImmutableMap.<Integer, Character>builder()
            .put(0, '0').put(1, '1').put(2, '2').put(3, '3').put(4, '4')
            .put(5, '5').put(6, '6').put(7, '7').put(8, '8').put(9, '9')
            .put(10, 'a').put(11, 'b').put(12, 'c').put(13, 'd').put(14, 'e')
            .put(15, 'f').put(16, 'g').put(17, 'h').put(18, 'i').put(19, 'j')
            .put(20, 'k').put(21, 'l').put(22, 'm').put(23, 'n').put(24, 'o')
            .put(25, 'p').put(26, 'q').put(27, 'r').put(28, 's').put(29, 't')
            .put(30, 'u').put(31, 'v').put(32, 'w').put(33, 'x').put(34, 'y')
            .put(35, 'z').put(36, 'A').put(37, 'B').put(38, 'C').put(39, 'D')
            .put(40, 'E').put(41, 'F').put(42, 'G').put(43, 'H').put(44, 'I')
            .put(45, 'J').put(46, 'K').put(47, 'L').put(48, 'M').put(49, 'N')
            .put(50, 'O').put(51, 'P').put(52, 'Q').put(53, 'R').put(54, 'S')
            .put(55, 'T').put(56, 'U').put(57, 'V').put(58, 'W').put(59, 'X')
            .put(60, 'Y').put(61, 'Z')
            .build();

    public static String encode(String input) {
        int hash32 = Math.abs(Hashing.murmur3_32_fixed().hashString(input, StandardCharsets.UTF_8).asInt());
        StringBuilder sb = new StringBuilder();
        while (hash32 != 0) {
            sb.append(base62Map.get(hash32 % BASE));
            hash32 /= BASE;
        }
        return sb.toString();
    }
}
