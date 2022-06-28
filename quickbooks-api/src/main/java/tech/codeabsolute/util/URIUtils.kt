package tech.codeabsolute.util

import org.apache.http.NameValuePair
import org.apache.http.client.utils.URLEncodedUtils
import java.net.URI
import java.nio.charset.Charset

class URIUtils {

    fun getAttributesFromURI(uri: URI): MutableList<NameValuePair> {
        return URLEncodedUtils.parse(uri, Charset.defaultCharset())
    }
}