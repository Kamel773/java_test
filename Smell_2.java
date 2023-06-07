private static String doHttpAction(String data,boolean json,boolean post,String url){
  HttpURLConnection connection=null;
  DataOutputStream os=null;
  InputStream is=null;
  try {
    URL sUrl=new URL(url);
    connection=(HttpURLConnection)sUrl.openConnection();
    connection.setConnectTimeout(CONNECT_TIMEOUT_TIME);
    connection.setReadTimeout(READ_TIMEOUT_TIME);
    if (post) {
      connection.setRequestMethod("POST");
    }
 else {
      connection.setRequestMethod("GET");
    }
    connection.setDoInput(true);
    connection.setDoOutput(true);
    connection.setUseCaches(false);
    connection.setInstanceFollowRedirects(true);
    if (json) {
      connection.setRequestProperty("Content-Type","application/json");
    }
 else {
      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      connection.setRequestProperty("Content-Length",data.length() + "");
    }
    connection.connect();
    os=new DataOutputStream(connection.getOutputStream());
    os.write(data.getBytes(),0,data.getBytes().length);
    os.flush();
    os.close();
    is=connection.getInputStream();
    Scanner scan=new Scanner(is);
    scan.useDelimiter("\\A");
    if (scan.hasNext())     return scan.next();
  }
 catch (  Exception e) {
    e.printStackTrace();
  }
 finally {
    if (connection != null)     connection.disconnect();
    if (os != null) {
      try {
        os.close();
      }
 catch (      IOException e) {
        e.printStackTrace();
      }
    }
    if (is != null) {
      try {
        is.close();
      }
 catch (      IOException e) {
        e.printStackTrace();
      }
    }
  }
  return null;
}
