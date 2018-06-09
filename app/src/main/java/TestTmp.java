import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.AttributeFactory;

import java.io.IOException;
import java.io.StringReader;

public class TestTmp {

    public static void main(String[] args) throws IOException {
        AttributeFactory factory = AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY;
        StandardTokenizer tokenizer = new StandardTokenizer(factory);
        tokenizer.setReader(new StringReader("\uD862\uDE0F"));
        CharTermAttribute charTermAttribute = tokenizer.addAttribute(CharTermAttribute.class);

//        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
//        TokenStream tokenStream = standardAnalyzer.tokenStream("test", "\uD862\uDE0F你好");
//        CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
//        OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
        tokenizer.reset();
        while (tokenizer.incrementToken()) {
//            int startOffset = offsetAttribute.startOffset();
//            int endOffset = offsetAttribute.endOffset();
//            System.out.println(startOffset);
//            System.out.println(endOffset);
            String term = charTermAttribute.toString();
            System.out.println(term);
        }

    }
}

