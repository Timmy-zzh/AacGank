package com.timmy.baselib.image;

/**
 * Created by zhangyang on 2017/11/16.
 * <p>
 * 图片上传助手类
 */
public class UploadHelper {

    /**
     * 上传单张图片
     *
     * @param path
     * @return
     */
//    public static Observable<BaseResult<String>> uploadSingleImg(final String path) {
//        return Observable.just(path)
//                .flatMap(new Function<String, ObservableSource<BaseResult<String>>>() {
//                    @Override
//                    public ObservableSource<BaseResult<String>> apply(@NonNull String path) throws Exception {
//                        if (TextUtils.isEmpty(path)) {
//                            return Observable.error(new Exception("图片文件不存在！"));
//                        }
//                        if (path.startsWith("http")) {//网络
//                            BaseResult<String> result = new BaseResult<>();
//                            result.setStatusCode(0);
//                            result.setData(path);
//                            return Observable.just(result);
//                        }
//
//                        File file = new File(path);
//                        if (!file.exists()) {
//                            return Observable.error(new Exception("图片文件不存在！"));
//                        }
//
//                        File compressed = null;
//                        try {
////                            compressed = Luban.with(Utils.getContext()).load(file).get();
//                            BitmapFactory.decodeFile("");
//                        } catch (Exception e) {
////                            e.printStackTrace();
//                            return Observable.error(new Exception("图片压缩异常，请重试!"));
//                        }
//
//                        RequestBody body = RequestBody.create(MediaType.parse("image/*"), compressed);
//                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
//
//                        return HttpProvider.quickCreate(CommonApi.class)
//                                .uploadImag(part)
//                                .compose(BTOTransformer.<BaseResult<String>>get());
//                    }
//                });
//    }
//
//    /**
//     * 上传多张图片,按传入的图片本地路径顺序返回上传成功后的网络图片路径,
//     * 路径集合可以是本地路径与网络路径的混合，这里会进行筛选，选出本地路径进行上传，
//     * 然后按顺序返回上传成功的和本就已存在的网络路径集合
//     *
//     * @param paths
//     * @return
//     */
//    public static Observable<List<String>> uploadImags(List<String> paths) {
//        return Observable.just(new ArrayList<String>(paths))
//                .flatMap(new Function<List<String>, ObservableSource<List<String>>>() {
//                    @Override
//                    public ObservableSource<List<String>> apply(@NonNull List<String> paths) throws Exception {
//                        if (paths.isEmpty()) {
//                            return Observable.just(paths);
//                        }
//                        //用于保存路径集合中的网络路径，用于后面上传成功后的路径排序，
//                        // 保证传进来的本地路径对于的上传成功后的网络路径在集合中的顺序不变
//                        final Map<Integer, String> remoteImgPath = new HashMap<Integer, String>();
//                        Map<String, RequestBody> map = new HashMap<>();
//                        int emptyCount = 0;
//                        for (int i = 0; i < paths.size(); i++) {
//                            String path = paths.get(i);
//                            if (TextUtils.isEmpty(path)) {
//                                emptyCount++;
//                                continue;
//                            }
//                            if (path.startsWith("http")) {
//                                remoteImgPath.put(i - emptyCount, path);
//                                continue;
//                            }
//
//                            File file = new File(path);
//                            if (!file.exists()) {
//                                return Observable.error(new Exception("图片文件不存在！"));
//                            }
//
//                            File compressed = null;
//                            try {
//                                compressed = Luban.with(HMApplication.get()).load(file).get();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                return Observable.error(new Exception("图片压缩异常，请重试!"));
//                            }
//
//                            RequestBody body = RequestBody.create(MediaType.parse("image/*"), compressed);
//                            map.put("file\"; filename=\"" + file.getName() + i + "", body);
//                        }
//
//                        if (map.isEmpty()) {
//                            if (remoteImgPath.isEmpty()) {
//                                List<String> empty = Collections.emptyList();
//                                return Observable.just(empty);
//                            } else {
//                                //走到这里，说明传进来的路径集合全是网络路径，没有需要上传的本地路径
//                                return Observable.just(paths);
//                            }
//                        } else {
//                            return uploadImagesNow(map, paths);
//                        }
//                    }
//                }).compose(BTOTransformer.<List<String>>get());
//    }
//
//    /**
//     * 上传图片，上传图片网络请求，并对返回结果进行处理
//     *
//     * @param map
//     * @return
//     */
//    private static Observable<List<String>> uploadImagesNow(Map<String, RequestBody> map, final List<String> imageData) {
//        return HttpProvider.quickCreate(CommonApi.class)
//                .uploadImags(map)
//                .flatMap(new Function<BaseResult<List<String>>, ObservableSource<List<String>>>() {
//                    @Override
//                    public ObservableSource<List<String>> apply(@NonNull BaseResult<List<String>> result) throws Exception {
//                        if (result == null) {
//                            return Observable.error(new Exception("服务器开小差了，请稍后重试！"));
//                        }
//                        if (!result.isSuccess()) {
//                            return Observable.error(new Exception(result.getMsg()));
//                        }
//                        final List<String> data = result.getData();
//                        if (data == null || data.isEmpty()) {
//                            return Observable.error(new Exception("服务器开小差了，请稍后重试！"));
//                        }
//                        //将集合中的本地路径替换成对应的网络路径
//                        List<String> copyImageData = new ArrayList<String>(imageData);
//                        for (int i = 0; i < copyImageData.size(); i++) {
//                            String localPath = copyImageData.get(i);
//                            if (!data.isEmpty() && !localPath.startsWith("http")) {
//                                imageData.remove(i);
//                                imageData.add(i, data.remove(0));
//                            }
//                        }
//                        return Observable.just(imageData);
//                    }
//                }).compose(BTOTransformer.<List<String>>get());
//    }

}
