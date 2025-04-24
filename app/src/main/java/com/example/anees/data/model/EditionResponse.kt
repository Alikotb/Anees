package com.example.anees.data.model

data class EditionResponse(
    val hadiths: List<Hadith>,
    val metadata: Metadata
) {
    data class Hadith(
        val arabicnumber: Int,
        val grades: List<Grade>,
        val hadithnumber: Int,
        val reference: Reference,
        val text: String
    ) {
        data class Grade(
            val grade: String,
            val name: String
        )

        data class Reference(
            val book: Int,
            val hadith: Int
        )
    }

    data class Metadata(
        val name: String,
        val section_details: SectionDetails,
        val sections: Sections
    ) {
        data class SectionDetails(
            val `0`: X0,
            val `1`: X1,
            val `10`: X10,
            val `11`: X11,
            val `12`: X12,
            val `13`: X13,
            val `14`: X14,
            val `15`: X15,
            val `16`: X16,
            val `17`: X17,
            val `18`: X18,
            val `19`: X19,
            val `2`: X2,
            val `20`: X20,
            val `21`: X21,
            val `22`: X22,
            val `23`: X23,
            val `24`: X24,
            val `25`: X25,
            val `26`: X26,
            val `27`: X27,
            val `28`: X28,
            val `29`: X29,
            val `3`: X3,
            val `30`: X30,
            val `31`: X31,
            val `32`: X32,
            val `33`: X33,
            val `34`: X34,
            val `35`: X35,
            val `36`: X36,
            val `37`: X37,
            val `38`: X38,
            val `39`: X39,
            val `4`: X4,
            val `40`: X40,
            val `41`: X41,
            val `42`: X42,
            val `43`: X43,
            val `44`: X44,
            val `45`: X45,
            val `46`: X46,
            val `47`: X47,
            val `48`: X48,
            val `49`: X49,
            val `5`: X5,
            val `50`: X50,
            val `51`: X51,
            val `52`: X52,
            val `53`: X53,
            val `54`: X54,
            val `55`: X55,
            val `56`: X56,
            val `57`: X57,
            val `58`: X58,
            val `59`: X59,
            val `6`: X6,
            val `60`: X60,
            val `61`: X61,
            val `7`: X7,
            val `8`: X8,
            val `9`: X9
        ) {
            data class X0(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X1(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X10(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X11(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X12(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X13(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X14(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X15(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X16(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X17(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X18(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X19(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X2(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X20(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X21(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X22(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X23(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X24(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X25(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X26(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X27(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X28(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X29(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X3(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X30(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X31(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X32(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X33(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X34(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X35(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X36(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X37(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X38(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X39(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X4(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X40(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X41(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X42(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X43(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X44(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X45(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X46(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X47(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X48(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X49(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X5(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X50(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X51(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X52(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X53(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X54(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X55(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X56(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X57(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X58(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X59(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X6(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X60(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X61(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X7(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X8(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )

            data class X9(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )
        }

        data class Sections(
            val `0`: String,
            val `1`: String,
            val `10`: String,
            val `11`: String,
            val `12`: String,
            val `13`: String,
            val `14`: String,
            val `15`: String,
            val `16`: String,
            val `17`: String,
            val `18`: String,
            val `19`: String,
            val `2`: String,
            val `20`: String,
            val `21`: String,
            val `22`: String,
            val `23`: String,
            val `24`: String,
            val `25`: String,
            val `26`: String,
            val `27`: String,
            val `28`: String,
            val `29`: String,
            val `3`: String,
            val `30`: String,
            val `31`: String,
            val `32`: String,
            val `33`: String,
            val `34`: String,
            val `35`: String,
            val `36`: String,
            val `37`: String,
            val `38`: String,
            val `39`: String,
            val `4`: String,
            val `40`: String,
            val `41`: String,
            val `42`: String,
            val `43`: String,
            val `44`: String,
            val `45`: String,
            val `46`: String,
            val `47`: String,
            val `48`: String,
            val `49`: String,
            val `5`: String,
            val `50`: String,
            val `51`: String,
            val `52`: String,
            val `53`: String,
            val `54`: String,
            val `55`: String,
            val `56`: String,
            val `57`: String,
            val `58`: String,
            val `59`: String,
            val `6`: String,
            val `60`: String,
            val `61`: String,
            val `7`: String,
            val `8`: String,
            val `9`: String
        )
    }
}