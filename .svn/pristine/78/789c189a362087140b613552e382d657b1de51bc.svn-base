package net.discuz.asynctask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.DB;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.TopSiteDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * 更新头1000个站点
 * 
 * @author lkh
 * 
 */
public class UpdateTop1000Site
{

	public UpdateTop1000Site(DiscuzBaseActivity discuzbaseactivity)
	{
		CheckTop1000TimestampListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				app.removeHttpConnListener(filter);
			}

			public void onSucceed(String s, String s1)
			{
				boolean flag;
				boolean flag2;
				long l2;
				flag = true;
				boolean flag1;
				boolean flag3;
				boolean flag4;
				HttpConnThread httpconnthread;
				String s2;
				long l;
				String s3;
				long l1;
				try
				{
					flag1 = Tools.stringIsNullOrEmpty(s);
				} catch (Exception exception)
				{
					exception.printStackTrace();
					return;
				}
				flag2 = false;
				if (!flag1)
				{
					flag3 = s.contains("code");
					flag2 = false;
					if (flag3)
					{
						flag4 = s.contains("res");
						flag2 = false;
						if (flag4)
						{
							try
							{
								l = (new JSONObject(s)).optLong("res");
								s3 = GlobalDBHelper.getInstance().getValue(
										"is_need_update_topsite_timestamp");
								if (s3 != null)
								{
									l1 = Long.valueOf(s3).longValue();
									l2 = l1;
									if (l <= 0L || l <= l2)
										flag = false;
									flag2 = flag;
								}
							} catch (Exception e)
							{
								e.printStackTrace();
								flag2 = false;
							}

						}
					}
				}
				if (!flag2)
					if (_needToUpdate() || getLocalTop1000SiteCout() == 0)
					{
						httpconnthread = new HttpConnThread(null, 0);
						if (activity != null)
						{
							httpconnthread
									.setUrl((new StringBuilder())
											.append("http://api.discuz.qq.com/mobile/site/topList?")
											.append(Core
													.getInstance()
													._getParamsSig(
															new String[] { "num=1000" }))
											.toString());
							httpconnthread.setCacheType(-1);
							s2 = getClass().getSimpleName();
							httpconnthread.setFilter(s2);
							app.setHttpConnListener(s2, UpdateTop1000Listener);
							app.sendHttpConnThread(httpconnthread);
						}
					}
				app.removeHttpConnListener(filter);
				return;
			}
		};
		UpdateTop1000Listener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{
			public void onFailed(Exception exception)
			{}

			public void onSucceed(String s, String s1)
			{
				if (!"error".equals(s) && s != null)
				{
					JSONArray jsonarray;
					ArrayList arraylist;
					SiteInfo siteinfo;
					SimpleDateFormat simpledateformat;
					try
					{
						jsonarray = (new JSONObject(s)).optJSONArray("res");
					} catch (Exception exception)
					{
						exception.printStackTrace();
						return;
					}
					if (jsonarray != null)
					{
						try
						{
							GlobalDBHelper.getInstance().replace(
									"is_first_open", "false");
							return;
						} catch (Exception ie)
						{
							ie.printStackTrace();
						}

						DB.writer.release();
						simpledateformat = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						GlobalDBHelper.getInstance().replace(
								"is_need_update_topsite_timestamp",
								String.valueOf(Tools._getTimeStampUnix()));
						GlobalDBHelper.getInstance().replace(
								"is_need_update_topsite",
								simpledateformat.format(Long
										.valueOf((new Date()).getTime())));
					} else
					{
						arraylist = new ArrayList();
						TopSiteDBHelper.getInstance().deleteAll();
						for (int i = 0; i < jsonarray.length(); i++)
						{
							siteinfo = new SiteInfo();
							try
							{
								siteinfo._initSearchValue(jsonarray
										.getJSONObject(i));
								if (!arraylist.contains(siteinfo))
									arraylist.add(siteinfo);
							} catch (Exception exception1)
							{}
						}
						try
						{
							DB.writer.acquire();
							TopSiteDBHelper.getInstance()
									.insertBatch(arraylist);
						} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally
						{
							DB.writer.release();
						}

					}
				}
			}
		};
		activity = discuzbaseactivity;
		app = DiscuzApp.getInstance();
		filter = getClass().getSimpleName();
	}

	private int getLocalTop1000SiteCout()
	{
		return TopSiteDBHelper.getInstance().getCount();
	}

	public boolean _needToUpdate()
	{
		String s = GlobalDBHelper.getInstance().getValue(
				"is_need_update_topsite");
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		if (!Tools.stringIsNullOrEmpty(s))
		{
			DEBUG.o((new StringBuilder())
					.append("=========is_need_update_topsite==========")
					.append(s).toString());
			long l;
			try
			{
				Date date = simpledateformat.parse(s);
				l = ((new Date()).getTime() - date.getTime()) / 0x5265c00L;
			} catch (Exception exception)
			{
				exception.printStackTrace();
				return true;
			}
			if (l <= 7L)
				return false;
		}
		return true;
	}

	public void loadDefaultSite()
	{
		Log.d("UpdateTop1000Site", "loadDefaultSite() ----> Enter");
		this.UpdateTop1000Listener
				.onSucceed(
						"{\"code\":0,\"res\":[{\"sId\":4231411,\"sName\":\"步步高互动社区\",\"sUrl\":\"http://bbs.vivo.com.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4625019,\"sName\":\"口袋巴士\",\"sUrl\":\"http://bbs.ptbus.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4473043,\"sName\":\"91手机\",\"sUrl\":\"http://ibbs.91.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1435464,\"sName\":\"手机中国\",\"sUrl\":\"http://bbs.cnmo.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4151859,\"sName\":\"许嵩官方论坛\",\"sUrl\":\"http://bbs.vaecn.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4352832,\"sName\":\"游侠网\",\"sUrl\":\"http://game.ali213.net/\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4000108,\"sName\":\"Discuz!官方论坛\",\"sUrl\":\"http://www.discuz.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4340258,\"sName\":\"28推\",\"sUrl\":\"http://bbs.28tui.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4305867,\"sName\":\"域名城\",\"sUrl\":\"http://club.domain.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":408943,\"sName\":\"淮网\",\"sUrl\":\"http://www.huainet.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4530696,\"sName\":\"太仓论坛\",\"sUrl\":\"http://www.bbstc.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4231411,\"sName\":\"步步高互动社区\",\"sUrl\":\"http://bbs.vivo.com.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4473043,\"sName\":\"91手机\",\"sUrl\":\"http://ibbs.91.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4352832,\"sName\":\"游侠网\",\"sUrl\":\"http://game.ali213.net/\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4305867,\"sName\":\"域名城\",\"sUrl\":\"http://club.domain.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4000108,\"sName\":\"Discuz!官方论坛\",\"sUrl\":\"http://www.discuz.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4050478,\"sName\":\"华人街\",\"sUrl\":\"http://www.huarenjie.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4696973,\"sName\":\"小春网\",\"sUrl\":\"http://www.xiaochuncnjp.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":261314,\"sName\":\"Chiphell - 分享与交流用户体验的�?��平台\",\"sUrl\":\"http://www.chiphell.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4761597,\"sName\":\"3DMGAME论坛\",\"sUrl\":\"http://bbs.3dmgame.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1236715,\"sName\":\"黑暗军团 - DFT 论坛\",\"sUrl\":\"http://www.darkforcesteam.com.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":35508,\"sName\":\"iMP3随身影音\",\"sUrl\":\"http://bbs.imp3.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4148504,\"sName\":\"摩托车交易\",\"sUrl\":\"http://www.moto8.cn/jiaoyi\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1479947,\"sName\":\"MIUI官方论坛\",\"sUrl\":\"http://www.miui.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":497757,\"sName\":\"编织人生论坛\",\"sUrl\":\"http://bbs.bianzhirensheng.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4986201,\"sName\":\"大成社区\",\"sUrl\":\"http://mycd.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5030976,\"sName\":\"Q宠大乐斗\",\"sUrl\":\"http://fight.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1471860,\"sName\":\"智机�?- Windows Phone论坛\",\"sUrl\":\"http://www.wpxap.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4963748,\"sName\":\"大豫社区\",\"sUrl\":\"http://myhenan.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4976635,\"sName\":\"第七大道官方论坛\",\"sUrl\":\"http://bbs.7road.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":99556,\"sName\":\"SiLUHD 思路论坛\",\"sUrl\":\"http://bbs.siluhd.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4880624,\"sName\":\"佳域宇�?官方论坛\",\"sUrl\":\"http://bbs.ejiayu.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1295824,\"sName\":\"四川论坛-麻辣社区\",\"sUrl\":\"http://www.mala.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5042264,\"sName\":\"红网论坛\",\"sUrl\":\"http://bbs.rednet.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5053585,\"sName\":\"QQ炫舞\",\"sUrl\":\"http://x5.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4995468,\"sName\":\"移动叔叔\",\"sUrl\":\"http://products.mobileuncle.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4987669,\"sName\":\"大湘社区 · 星生活\",\"sUrl\":\"http://myhn.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5020743,\"sName\":\"穿越火线\",\"sUrl\":\"http://cf.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":682985,\"sName\":\"游民星空论坛\",\"sUrl\":\"http://bbs.gamersky.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4872895,\"sName\":\"成都都市网社区\",\"sUrl\":\"http://www.028e.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5040797,\"sName\":\"QQ游戏\",\"sUrl\":\"http://qqgame.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4151859,\"sName\":\"许嵩(Vae)官方论坛\",\"sUrl\":\"http://bbs.vaecn.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5060710,\"sName\":\"盐城鹤鸣亭\",\"sUrl\":\"http://www.hmting.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":89352,\"sName\":\"合肥论坛\",\"sUrl\":\"http://bbs.hefei.cc\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5053650,\"sName\":\"萌三国\",\"sUrl\":\"http://meng.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1995,\"sName\":\"Nintendo World BBS\",\"sUrl\":\"http://bbs.newwise.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":683247,\"sName\":\"应届生求职招聘论坛\",\"sUrl\":\"http://bbs.yingjiesheng.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4270465,\"sName\":\"52pk游戏论坛\",\"sUrl\":\"http://bbs.52pk.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5042327,\"sName\":\"七雄争霸\",\"sUrl\":\"http://7.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5055737,\"sName\":\"Happyelements\",\"sUrl\":\"http://fc.happyelements.com/bbs\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5033786,\"sName\":\"地下城与勇士\",\"sUrl\":\"http://dnf.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":733469,\"sName\":\"宁海在线\",\"sUrl\":\"http://bbs.nhzj.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1358898,\"sName\":\"HDPfans高清播放机爱好�?\",\"sUrl\":\"http://www.hdpfans.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4406377,\"sName\":\"大渝社区-�?��庆，�?��爱的社区\",\"sUrl\":\"http://mycq.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5079111,\"sName\":\"11全图网\",\"sUrl\":\"http://www.11mh.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4953571,\"sName\":\"论谈外汇-特色外汇游戏、比赛�?策略-积分商城RMB、实物�?虚拟物品兑换-99%比例的返�?外汇-汇率-黄金价格-白银价格\",\"sUrl\":\"http://www.bbsmt4.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":277114,\"sName\":\"踏花行花卉论坛\",\"sUrl\":\"http://www.tahua.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4987494,\"sName\":\"大楚社区\",\"sUrl\":\"http://myhb.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5001167,\"sName\":\"御龙在天\",\"sUrl\":\"http://yl.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4997917,\"sName\":\"大粤社区\",\"sUrl\":\"http://mygd.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4955435,\"sName\":\"DCHome.net 數碼天地論壇\",\"sUrl\":\"http://www.dchome.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4424085,\"sName\":\"大闽社区\",\"sUrl\":\"http://myfj.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1621802,\"sName\":\"amoBBS 阿莫电子论坛\",\"sUrl\":\"http://www.amobbs.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4624887,\"sName\":\"eoeAndroid�?��者社区\",\"sUrl\":\"http://www.eoeandroid.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":621202,\"sName\":\"IT之家社区\",\"sUrl\":\"http://bbs.ithome.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4659972,\"sName\":\"小嘀咕游戏论�?CF刷雷�?逆战�?���?cf秒解防沉�?cf卡巨人城,yy个�?说明,cf卡bug教程,CF�?��BUG\",\"sUrl\":\"http://www.xiaodigu.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1406149,\"sName\":\"李维斯牛仔裤爱好者论坛\",\"sUrl\":\"http://bbs.ilevis.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":449435,\"sName\":\"蚌埠论坛\",\"sUrl\":\"http://www.ahbb.cc/bbs\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":138453,\"sName\":\"UG�?模具论坛-模具培训-\",\"sUrl\":\"http://bbs.uggd.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4473428,\"sName\":\"苏州大学我爱苏大论坛\",\"sUrl\":\"http://ppwww.52suda.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4002070,\"sName\":\"重庆溜\",\"sUrl\":\"http://bbs.cq6.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4313168,\"sName\":\"爬行天下\",\"sUrl\":\"http://bbs.pxtx.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4945645,\"sName\":\"蓝色河畔\",\"sUrl\":\"http://www.hepan.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1472628,\"sName\":\"湖南妈妈�?湖南长沙妈妈网上�?��交流社区\",\"sUrl\":\"http://www.hnmama.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1051282,\"sName\":\"珠宝大家坛\",\"sUrl\":\"http://www.giabbs.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5032917,\"sName\":\"QQ飞车\",\"sUrl\":\"http://speed.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4351122,\"sName\":\"挖财论坛_国内领先的个人记账理财社区\",\"sUrl\":\"http://www.wacai.com/club\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":657710,\"sName\":\"互动中国\",\"sUrl\":\"http://forum.china.com.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1306863,\"sName\":\"中国竞彩网论坛\",\"sUrl\":\"http://220.181.95.197\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4923387,\"sName\":\"东林书院\",\"sUrl\":\"http://bbs.thmz.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":37984,\"sName\":\"中国木工爱好者\",\"sUrl\":\"http://www.zuojiaju.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4990536,\"sName\":\"bbs.zgzcw.com\",\"sUrl\":\"http://bbs.zgzcw.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":179680,\"sName\":\"高明论坛 - 网聚高明人的力量 - www.528500.com\",\"sUrl\":\"http://www.528500.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4375467,\"sName\":\"真好论坛\",\"sUrl\":\"http://www.chinazhw.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4589567,\"sName\":\"泡泡恋爱学\",\"sUrl\":\"http://paoxue.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1304011,\"sName\":\"CA002二手器材交易网\",\"sUrl\":\"http://www.ca002.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1062982,\"sName\":\"煮机网\",\"sUrl\":\"http://bbs.zoopda.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4583666,\"sName\":\"千秋狩猎论坛\",\"sUrl\":\"http://www.solchn.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":2722,\"sName\":\"绿城社区\",\"sUrl\":\"http://www.i52nv.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":486663,\"sName\":\"TOGO论坛_合团网论坛_合肥论坛\",\"sUrl\":\"http://bbs.hftogo.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1188646,\"sName\":\"全集txt电子书免费下载_豆丁论坛\",\"sUrl\":\"http://bbs.docin.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":479788,\"sName\":\"互助�?互助旅行网：结识互友，快乐互助游天下！让旅行更省钱更便利！\",\"sUrl\":\"http://www.52huzhu.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":9,\"sName\":\"六西格玛品质网\",\"sUrl\":\"http://www.6sq.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1235404,\"sName\":\"泰无聊论坛\",\"sUrl\":\"http://bbs.t56.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":399939,\"sName\":\"株洲在线论坛\",\"sUrl\":\"http://home.z4bbs.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":5053625,\"sName\":\"寻侠\",\"sUrl\":\"http://xia.gamebbs.qq.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1309739,\"sName\":\"诸城信息�?- 城市论坛\",\"sUrl\":\"http://bbs.zcinfo.net\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4817520,\"sName\":\"纽曼手机官方论坛,纽曼N1,学生特供�?MTK6577,双核双卡,纽曼\",\"sUrl\":\"http://bbs.newman.mobi\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":488122,\"sName\":\"瑞安论坛\",\"sUrl\":\"http://bbs.ruian.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4762646,\"sName\":\"雅书阁\",\"sUrl\":\"http://www.toshu.cn\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":4913445,\"sName\":\"小猪快跑\",\"sUrl\":\"http://bbs.jumpcn.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0},{\"sId\":1443103,\"sName\":\"讯雷电影论坛\",\"sUrl\":\"http://bbs.akdy.com\",\"sVersion\":\"\",\"sCharset\":\"\",\"sYesterdayPost\":0}]}",
						null);
		Log.d("UpdateTop1000Site", "loadDefaultSite() ----> Exit");
	}

	/**
	 * 检查站点信息
	 */
	public void start()
	{
		HttpConnThread httpconnthread = new HttpConnThread(null, 0);
		if (activity != null)
		{
			httpconnthread.setUrl((new StringBuilder())
					.append("http://api.discuz.qq.com/mobile/blacklist/check?")
					.append(Core.getInstance()._getParamsSig(new String[0]))
					.toString());
			httpconnthread.setCacheType(-1);
			httpconnthread.setFilter(filter);
			app.setHttpConnListener(filter, CheckTop1000TimestampListener);
			app.sendHttpConnThread(httpconnthread);
		}
	}

	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener CheckTop1000TimestampListener;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener UpdateTop1000Listener;
	private DiscuzBaseActivity activity;
	private DiscuzApp app;
	private String filter;

}
// 2131296256