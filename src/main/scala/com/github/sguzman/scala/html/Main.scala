package com.github.sguzman.scala.html

import java.net.URL

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scalaj.http.Http


object Main {
  def url(args: Array[String]) =
    Config().parse(args, init = CmdArgs()).get.url

  def main(args: Array[String]): Unit =
    JsoupBrowser()
      .parseString(
        Http(new URL(url(args))
            .toString)
          .asString.body)
      .>>(elementList("""a[href]"""))
      .map(_.attr("href")) foreach println
}
