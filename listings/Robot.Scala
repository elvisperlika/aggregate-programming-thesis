package it.unibo.demo.robot

import java.net.URLEncoder
import upickle.default.ReadWriter
trait Robot:
  def id: Int
  def spinRight(): Unit
  def spinLeft(): Unit
  def forward(): Unit
  def backward(): Unit
  def nop(): Unit
  def intensities(left: Double, right: Double): Unit

case class WaveRobot(ip: String, val id: Int) extends Robot derives ReadWriter:
  
  private var lastCommandWasNoOp = false
  def spinRight(): Unit =
    requests.get(url = s"http://$ip/js?json=${Command(0.20, -0.20).toJson}")
    lastCommandWasNoOp = false
  ...
  override def nop(): Unit =
    if !lastCommandWasNoOp then requests.get(url = s"http://$ip/js?json=${Command(0, 0).toJson}")
    else ()
    lastCommandWasNoOp = true
  private class Command(left: Double, right: Double):
    def toJson: String = URLEncoder.encode(s"""{"T":1, "L":$left, "R":$right}""")

/**
 *
 * @param physicalId is the physical Id
 * @param id is the arUco Id
 */
case class ThymioRobot(val physicalId: String, val id: Int, val name: String) extends Robot derives ReadWriter:
  override def spinRight(): Unit =
    requests.get(url = s"http://localhost:52000/thymio?json=${Command(physicalId, +50, -50).toJson}")
  ...
  override def nop(): Unit =
    requests.get(url = s"http://localhost:52000/thymio?json=${Command(physicalId, 0, 0).toJson}")
  override def intensities(left: Double, right: Double): Unit = {}

  private class Command(physicalId: String, left: Double, right: Double):
    def toJson: String = URLEncoder.encode(s"""{"id":"$physicalId", "l":$left, "r":$right}""")
  
  