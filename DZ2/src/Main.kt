import com.sun.tools.javac.Main

import java.time.LocalDate

fun main() {

    val president = President(
        name = "Ivan",
        age = 55,
        salary = 55000.0,
        nationality = "Croatian",
        startDate = LocalDate.of(2022, 6, 1),
        termYears = 4
    )

    println("President info:")
    println(president)
    president.printTimeLeft()
    println("----------\n")

    val manager = Manager(
        name = "Marko",
        age = 45,
        salary = 48000.0,
        nationality = "Croatian",
        winRate = 56.67
    )

    println("Manager work:")
    println(manager.doWork())
    println("----------\n")

    val stadium = Stadium(
        name = "Maksimir",
        capacity = 35000,
        builtYear = 2000
    )

    val player1 = Footballer(
        name = "Petar",
        age = 25,
        nationality = "Croatian",
        salary = 30000.0,
        position = Position.ATTACKER,
        shirtNumber = 9,
        stats = FootballerStatistics(0, 0, 0, 0, 0)
    )

    val player2 = Footballer(
        name = "Luka",
        age = 27,
        nationality = "Croatian",
        salary = 32000.0,
        position = Position.MIDFIELDER,
        shirtNumber = 10,
        stats = FootballerStatistics(0, 0, 0, 0, 0)
    )

    val player3 = Footballer(
        name = "Ivan",
        age = 24,
        nationality = "Croatian",
        salary = 28000.0,
        position = Position.DEFENDER,
        shirtNumber = 5,
        stats = FootballerStatistics(0, 0, 0, 0, 0)
    )


    player1.scoreGoal()
    player1.scoreGoal()
    player1.playMatch()
    player1.getAssist()

    player2.playMatch()
    player2.getAssist()
    player2.getAssist()
    player2.getYellowCard()

    player3.playMatch()
    player3.getRedCard()

    val fan1 = Fan("Ana", 22, "Croatian", 1, "Petar")
    val fan2 = Fan("Ivan", 30, "Croatian", 2, "Luka")
    val fan3 = Fan("Maja", 25, "Croatian", 3, "Ivan")

    val team = FootballTeam(
        name = "Dinamo Zagreb",
        nationality = "Croatian",
        yearFounded = 1945,
        president = president,
        manager = manager,
        stadium = stadium,
        players = mutableListOf(player1, player2, player3),
        fans = mutableListOf(fan1, fan2, fan3)
    )

    println("----- TEAM INFO -----")
    println(team)

    println("Top scorers:")
    team.sortByGoalsScored().forEach {
        println("${it.name} - Goals: ${it.stats.goals}, Assists: ${it.stats.assists}")
    }

    println("\nTop assisters:")
    team.sortByAssists().forEach {
        println("${it.name} - Assists: ${it.stats.assists}, Goals: ${it.stats.goals}")
    }

    println("\nTop scorer: ${team.getTopScorer().name}")
    println("Top assister: ${team.getTopAssister().name}")

    val fan4 = Fan("Tina", 28, "Croatian", 4, "Petar")
    println("\nAdding a fan: ${fan4.name}")
    team.addFan(fan4)
    println("Total fans: ${team.fans.size}")

    println("Removing fan: ${fan2.name}")
    team.removeFan(fan2)
    println("Total fans: ${team.fans.size}")


    val awayTeam = FootballTeam(
        name = "Hajduk Split",
        nationality = "Croatian",
        yearFounded = 1911,
        president = president,
        manager = manager,
        stadium = stadium,
        players = mutableListOf(),
        fans = mutableListOf()
    )

    val match = Match(
        homeTeam = team,
        awayTeam = awayTeam,
        homeGoals = 2,
        awayGoals = 1,
        stadium = stadium,
        referee = Referee("Nikola", 40, "Croatian", 100000.0)
    )

    println("\nMatch info:")
    println(match)
    println(match.getResult())
}