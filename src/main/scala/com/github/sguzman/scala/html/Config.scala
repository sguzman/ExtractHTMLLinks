package com.github.sguzman.scala.html

case class Config(title: String = "ExtractHTMLLinks") extends scopt.OptionParser[CmdArgs](title) {
  head(title, "1.0")

  opt[String]('u', "url")
    .text("URL")
    .required()
    .valueName("<url>")
    .minOccurs(1)
    .maxOccurs(1)
    .action((x, c) => c.copy(url = x))

  help("help")
    .text("this menu")
}