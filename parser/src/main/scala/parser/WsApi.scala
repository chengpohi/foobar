package parser

/**
  * Default (Template) Project
  * Created by chengpohi on 3/20/16.
  */
object WsApi extends fastparse.WhitespaceApi.Wrapper({
  import fastparse.all._
  CharIn(" \n").rep
})
