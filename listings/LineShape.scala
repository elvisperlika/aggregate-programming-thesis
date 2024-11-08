class LineFormation(distanceThreshold: Double, leaderSelected: Int, stabilityThreshold: Double)
    extends ShapeFormation(leaderSelected, stabilityThreshold):
  override def calculateSuggestion(ordered: List[(Int, (Double, Double))]): Map[Int, (Double, Double)] =
    val (leftSlots, rightSlots) = ordered.indices.splitAt(ordered.size / 2)
    var devicesAvailable = ordered
    val leftCandidates = leftSlots
      .map: index =>
        val candidate = devicesAvailable
          .map:
            case (id, (xPos, yPos)) =>
              val newPos @ (newXpos, newYpos) = ((-(index + 1) * distanceThreshold) + xPos, yPos)
              val modulo = math.sqrt((newXpos * newXpos) + (newYpos * newYpos))
              (id, modulo, newPos)
          .minBy(_._2)
        devicesAvailable = devicesAvailable.filterNot(_._1 == candidate._1)
        candidate._1 -> candidate._3
      .toMap
    val rightCandidates = rightSlots
      .map(i => i - rightSlots.min)
      .map: index =>
        val candidate = devicesAvailable
          .map:
            case (id, (xPos, yPos)) =>
              val newPos @ (newXpos, newYpos) = (((index + 1) * distanceThreshold) + xPos, yPos)
              val modulo = math.sqrt((newXpos * newXpos) + (newYpos * newYpos))
              (id, modulo, newPos)
          .minBy(_._2)
        devicesAvailable = devicesAvailable.filterNot(_._1 == candidate._1)
        candidate._1 -> candidate._3
      .toMap
    leftCandidates ++ rightCandidates