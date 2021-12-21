package com.madcookie.holostation.data

import com.madcookie.holostation.R


object Repository {

    val channelList = listOf(
        VChannel("UCoSrY_IQQVpmIRZ9Xf-y93g", "Gawr Gura", false, Group.EN, Generation.GEN1, R.drawable.gura),
        VChannel("UCMwGHR0BTZuLsmjY_NT5Pwg", "Ninomae Ina'nis", false, Group.EN, Generation.GEN1, R.drawable.ina),
        VChannel("UCHsx4Hqa-1ORjQTh9TYDhww", "Takanashi Kiara", false, Group.EN, Generation.GEN1, R.drawable.kiara),
        VChannel("UCL_qhgtOy0dy1Agp8vkySQg", "Mori Calliope", false, Group.EN, Generation.GEN1, R.drawable.calli),
        VChannel("UCyl1z3jo3XHR1riLFKG5UAg", "Watson Amelia", false, Group.EN, Generation.GEN1, R.drawable.ame),
        VChannel("UC8rcEBzJSleTkf_-agPM20g", "IRyS", false, Group.EN, Generation.HOPE, R.drawable.irys),
        VChannel("UCgmPnx-EEeOrZSg5Tiw7ZRQ", "Hakos Baelz", false, Group.EN, Generation.GEN2, R.drawable.belz),
        VChannel("UCsUj0dszADCGbF3gNrQEuSQ", "Tsukumo Sana", false, Group.EN, Generation.GEN2, R.drawable.sana),
        VChannel("UCO_aKKYxn4tvrqPjcTzZ6EQ", "Ceres Fauna", false, Group.EN, Generation.GEN2, R.drawable.fauna),
        VChannel("UCmbs8T6MWqUHP1tIQvSgKrg", "Ouro Kronii", false, Group.EN, Generation.GEN2, R.drawable.kronii),
        VChannel("UC3n5uGu18FoCy23ggWWp8tA", "Nanashi Mumei", false, Group.EN, Generation.GEN2, R.drawable.mumei),
        VChannel("UCp6993wxpyDPHUpavwDFqgg", "Sora", false, Group.JP, Generation.GEN0, R.drawable.sora),
        VChannel("UCDqI2jOz0weumE8s7paEk6g", "Roboco", false, Group.JP, Generation.GEN0, R.drawable.roboco),
        VChannel("UC5CwaMl1eIgY8h02uZw7u8A", "Suisei", false, Group.JP, Generation.GEN0, R.drawable.suisei),
        VChannel("UC-hM6YJuNYVAmUWxeIr9FeA", "Miko", false, Group.JP, Generation.GEN0, R.drawable.miko),
        VChannel("UCD8HOxPs4Xvsm8H0ZxXGiBw", "Mel", false, Group.JP, Generation.GEN1, R.drawable.mel),
        VChannel("UCFTLzh12_nrtzqBPsTCqenA", "Aki rosenthal", false, Group.JP, Generation.GEN1, R.drawable.aki),
        VChannel("UC1CfXB_kRs3C-zaeTG3oGyg", "HAACHAMA", false, Group.JP, Generation.GEN1, R.drawable.hachama),
        VChannel("UCdn5BQ06XqgXoAxIhbqw5Rg", "Fubuki", false, Group.JP, Generation.GEN1, R.drawable.fubuki),
        VChannel("UCQ0UDLQCjY0rmuxCDE38FGg", "Matsuri", false, Group.JP, Generation.GEN1, R.drawable.matsuri),
        VChannel("UC1opHUrw8rvnsadT-iGp7Cg", "Aqua", false, Group.JP, Generation.GEN2, R.drawable.aqua),
        VChannel("UCXTpFs_3PqI41qX2d9tL2Rw", "Shion", false, Group.JP, Generation.GEN2, R.drawable.shion),
        VChannel("UC7fk0CB07ly8oSl0aqKkqFg", "Nakiri Ayame", false, Group.JP, Generation.GEN2, R.drawable.ayame),
        VChannel("UC1suqwovbL1kzsoaZgFZLKg", "Choco", false, Group.JP, Generation.GEN2, R.drawable.choco),
        VChannel("UCvzGlP9oQwU--Y0r9id_jnA", "Subaru", false, Group.JP, Generation.GEN2, R.drawable.subaru),
        VChannel("UCp-5t9SrOQwXMU7iIjQfARg", "Mio", false, Group.JP, Generation.GAMERS, R.drawable.mio),
        VChannel("UCvaTdHTWBGv3MKj3KVqJVCw", "Okayu", false, Group.JP, Generation.GAMERS, R.drawable.okayu),
        VChannel("UChAnqc_AY5_I3Px5dig3X1Q", "Korone", false, Group.JP, Generation.GAMERS, R.drawable.korone),
        VChannel("UC1DCedRgGHBdm81E1llLhOQ", "Pekora", false, Group.JP, Generation.GEN3, R.drawable.pekora),
        VChannel("UCl_gCybOJRIgOXw6Qb4qJzQ", "Rushia", false, Group.JP, Generation.GEN3, R.drawable.rushia),
        VChannel("UCvInZx9h3jC2JzsIzoOebWg", "Flare", false, Group.JP, Generation.GEN3, R.drawable.flare),
        VChannel("UCdyqAaZDKHXg4Ahi7VENThQ", "Noel", false, Group.JP, Generation.GEN3, R.drawable.noel),
        VChannel("UCCzUftO8KOVkV4wQG1vkUvg", "Marine", false, Group.JP, Generation.GEN3, R.drawable.marin),
        VChannel("UCqm3BQLlJfvkTsX_hvm0UmA", "Watame", false, Group.JP, Generation.GEN4, R.drawable.watame),
        VChannel("UC1uv2Oq6kNxgATlCiez59hw", "Towa", false, Group.JP, Generation.GEN4, R.drawable.towa),
        VChannel("UCa9Y57gfeY0Zro_noHRVrnw", "Luna", false, Group.JP, Generation.GEN4, R.drawable.luna),
        VChannel("UCFKOVgVbGmX65RxO3EtH3iw", "Lamy", false, Group.JP, Generation.GEN5, R.drawable.lamy),
        VChannel("UCAWSyEs_Io8MtpY3m-zqILA", "Nene", false, Group.JP, Generation.GEN5, R.drawable.nene),
        VChannel("UCUKD-uaobj9jiqB-VXt71mA", "Botan", false, Group.JP, Generation.GEN5, R.drawable.botan),
        VChannel("UCK9V2B22uJYu3N7eR_BT9QA", "Polka", false, Group.JP, Generation.GEN5, R.drawable.polka),
        VChannel("UCENwRMx5Yh42zWpzURebzTw", "Laplus", false, Group.JP, Generation.GEN6, R.drawable.laplus),
        VChannel("UCs9_O1tRPMQTHQ-N_L6FU2g", "Lui", false, Group.JP, Generation.GEN6, R.drawable.lui),
        VChannel("UC6eWCld0KwmyHFbAqK3V-Rw", "Koyori", false, Group.JP, Generation.GEN6, R.drawable.koyori),
        VChannel("UCIBY1ollUsauvVi4hW4cumw", "Chloe", false, Group.JP, Generation.GEN6, R.drawable.chloe),
        VChannel("UC_vMYWcDjmfdpH6r4TTn1MQ", "Iroha", false, Group.JP, Generation.GEN6, R.drawable.iroha)
    )
}