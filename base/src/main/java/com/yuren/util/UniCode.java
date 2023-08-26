package com.yuren.util;

/**
 * 创建日期: 2018/9/5 0005 on 下午 2:35
 * Created by 徐 Administrator
 * 描述：
 */
public class UniCode {
    /**
     * unicode编码
     *
     * @param string
     * @return String
     */
    public static String encodeUnicode(String string) {

      /*  StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u00" + Integer.toHexString(c));
        }

        return unicode.toString();*/


        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            unicode.append("\\u" + Integer.toHexString(c));
        }
        String str = unicode.toString();

        return str.replaceAll("\\\\", "0x");
    }

    /**
     * unicode 解码
     *
     * @param str
     * @return String
     */
    public static String decodeUnicode(String str) {
        if (StringUtil.isEmpty(str)) {
            return "";
        }
       /* Charset set = Charset.forName("UTF-8");
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher m = p.matcher( str );
        int start = 0 ;
        int start2 = 0 ;
        StringBuffer sb = new StringBuffer();
        while( m.find( start ) ) {
            start2 = m.start() ;
            if( start2 > start ){
                String seg = str.substring(start, start2) ;
                sb.append( seg );
            }
            String code = m.group( 1 );
            int i = Integer.valueOf( code , 16 );
            byte[] bb = new byte[ 4 ] ;
            bb[ 0 ] = (byte) ((i >> 8) & 0xFF );
            bb[ 1 ] = (byte) ( i & 0xFF ) ;
            ByteBuffer b = ByteBuffer.wrap(bb);
            sb.append( String.valueOf( set.decode(b) ).trim() );
            start = m.end() ;
        }
        start2 = str.length() ;
        if( start2 > start ){
            String seg = str.substring(start, start2) ;
            sb.append( seg );
        }
        return sb.toString() ;*/

        String str1 = str.replace("0x", "\\");

        StringBuffer string = new StringBuffer();
        String[] hex = str1.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }
}
