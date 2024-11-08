 private val jsonString =
    os.read(os.pwd / "src" / "main" / "scala" / "it" / "unibo" / "demo" / "configuration" / "waveRobot.json")
  private val waveData = ujson.read(jsonString)
  private val robots = read[Seq[WaveRobot]](waveData).toList
  private val waveList = robots.map(_.id)

  private val thymioRobotJson =
    os.read(os.pwd / "src" / "main" / "scala" / "it" / "unibo" / "demo" / "configuration" / "thymioRobot.json")
  private val thymioData = ujson.read(thymioRobotJson)
  private val thymioRobots = read[Seq[ThymioRobot]](thymioData).toList
  private val thymioList = thymioRobots.map(_.id)

  private val list = thymioRobots ++ robots