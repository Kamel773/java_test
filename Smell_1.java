private ThreadUtils.SimpleTask<Long> createClearCacheTask(){
  return new ThreadUtils.SimpleTask<Long>(){
    @Override public Long doInBackground() throws Throwable {
      try {
        long len=0;
        File appDataDir=new File(PathUtils.getInternalAppDataPath());
        if (appDataDir.exists()) {
          String[] names=appDataDir.list();
          for (          String name : names) {
            if (!name.equals("lib")) {
              File file=new File(appDataDir,name);
              len+=FileUtils.getLength(file);
              FileUtils.delete(file);
              LogUtils.i("「" + file + "」 was deleted.");
            }
          }
        }
        String externalAppCachePath=PathUtils.getExternalAppCachePath();
        len+=FileUtils.getLength(externalAppCachePath);
        FileUtils.delete(externalAppCachePath);
        LogUtils.i("「" + externalAppCachePath + "」 was deleted.");
        return len;
      }
 catch (      Exception e) {
        ToastUtils.showLong(e.toString());
        return -1L;
      }
    }
    @Override public void onSuccess(    Long result){
      if (result != -1) {
        ToastUtils.showLong("Clear Cache: " + ConvertUtils.byte2FitMemorySize(result));
      }
    }
  }
;
} 
