package com.github.sguzman.scala.html

import java.net.URL

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scalaj.http.Http


object Main {
  def url(args: Array[String]) =
    Config().parse(args, init = CmdArgs()) match {
      case Some(v) => v.url
      case None => throw new Exception()
    }


  def port(u: URL) = if (u.getPort == -1) "" else ":" ++ u.getPort.toString
  def cons(u: URL) = s"${u.getProtocol}://${u.getAuthority}${port(u)}"
  def consp(u: URL) = s"${u.getProtocol}://${u.getAuthority}${port(u)}${u.getPath}"

  def scheme(u: URL) = JsoupBrowser()
    .parseString(
      Http(u.toString).asString.body)
    .>>(elementList("""a[href]"""))
    .map(_.attr("href"))
    .map(t => if (t.startsWith("/")) cons(u) ++ t else t)
    .map(t => if (t.startsWith("#")) consp(u) else t)

  def main(args: Array[String]): Unit =
    scheme(new URL(url(args))) foreach println
}
