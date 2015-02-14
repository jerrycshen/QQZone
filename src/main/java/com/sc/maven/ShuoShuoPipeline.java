package com.sc.maven;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.sc.dao.ShuoShuoDao;
import com.sc.domain.Comment;
import com.sc.domain.ShuoShuo;
import com.sc.utils.HelpUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author shenchao 从下载的jsonp中抽取json，并从中抽取数据并封装起来
 *
 */
public class ShuoShuoPipeline implements Pipeline {

	public void process(ResultItems resultItems, Task task) {
		String json = resultItems.get("json");
		String host_id = resultItems.get("host_id");
		
		List<ShuoShuo> shuoshuo_list = new ArrayList<ShuoShuo>();

		String shuoshuos = JsonPath.read(json, "$.msglist[*]").toString();
		JSONArray jsonArray = JSONArray.parseArray(shuoshuos);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			ShuoShuo shuoshuo = new ShuoShuo();

			shuoshuo.setHost_id(Long.parseLong(host_id));
			shuoshuo.setShuoshuo_id(jsonObject.getString("tid"));
			shuoshuo.setContent(jsonObject.getString("content"));
			shuoshuo.setCreateTime(jsonObject.getString("createTime"));
			shuoshuo.setCreate_time(jsonObject.getLongValue("created_time"));

			String client = jsonObject.getString("source_name");
			if (client.equals("")) {
				shuoshuo.setClient("PC");
			} else {
				shuoshuo.setClient(client);
			}

			String pic = jsonObject.getString("pic");
			if (null == pic) {
				shuoshuo.setPic(0);
			} else {
				shuoshuo.setPic(1);
			}
			
			List<Comment> comment_list = getAllComments(jsonObject.getString("commentlist"),jsonObject.getString("tid"));
			if (comment_list != null) {
				shuoshuo.setComment_list(comment_list);
			}

			shuoshuo_list.add(shuoshuo);

		}
		
		ShuoShuoDao dao = new ShuoShuoDao();
		dao.addShuoShuo(shuoshuo_list);

	}
	
	
	/**
	 * @param commentlist 评论的json字符串
	 * @return 格式化评论
	 */
	private List<Comment> getAllComments(String commentlist,String shuoshuo_id) {
		if (null != commentlist) {
			JSONArray array = JSONArray.parseArray(commentlist);

			List<Comment> comment_list = new ArrayList<Comment>();

			for (int j = 0; j < array.size(); j++) {
				JSONObject object = array.getJSONObject(j);

				try {
					Comment comment = new Comment();
					comment.setShuoshuo_id(shuoshuo_id);
					comment.setComment_content(object.getString("content"));
					comment.setComment_string_time(object
							.getString("createTime"));
					comment.setComment_long_time(object
							.getLongValue("create_time"));
					comment.setFriend_id(object.getLongValue("uin"));
					comment.setFriend_name(object.getString("name"));
					comment.setHost_id(Long.parseLong(HelpUtils.getHost_id()));

					comment_list.add(comment);
				} catch (Exception e) {
					continue;
				}
			}
			return comment_list;
		} 
		return null;
	}

}
