package byCodeGame.game.navigation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import byCodeGame.game.moudle.activity.action.ActivityShowActivityAction;
import byCodeGame.game.moudle.animal.action.EatAnimalAction;
import byCodeGame.game.moudle.animal.action.GetAnimalInfoAction;
import byCodeGame.game.moudle.arena.action.DareTargetAction;
import byCodeGame.game.moudle.arena.action.GetArenaDataAction;
import byCodeGame.game.moudle.arena.action.GetArenaTargetAction;
import byCodeGame.game.moudle.arena.action.GetFightLoadAction;
import byCodeGame.game.moudle.arena.action.GetLadderAction;
import byCodeGame.game.moudle.arena.action.GetNowLadderAction;
import byCodeGame.game.moudle.arena.action.SetFightHeroAction;
import byCodeGame.game.moudle.arena.action.TestArenaFightAction;
import byCodeGame.game.moudle.arms.action.ArmsChangeHeroSoldierAction;
import byCodeGame.game.moudle.arms.action.ArmsUpdateHeroSoldierLvAction;
import byCodeGame.game.moudle.arms.action.ArmsUpdateHeroSoldierQualityAction;
import byCodeGame.game.moudle.arms.action.ArmsUpdateHeroSoldierSkillAction;
import byCodeGame.game.moudle.babel.action.ChangeTroopDataAction;
import byCodeGame.game.moudle.babel.action.ChoiceBabelNpcAction;
import byCodeGame.game.moudle.babel.action.FightBabelAction;
import byCodeGame.game.moudle.babel.action.GetBabelInfoAction;
import byCodeGame.game.moudle.babel.action.GetHeroInfoAction;
import byCodeGame.game.moudle.babel.action.ResetTowerAction;
import byCodeGame.game.moudle.babel.action.ReviveHeroAction;
import byCodeGame.game.moudle.barrack.action.BarrackGetExploitAction;
import byCodeGame.game.moudle.barrack.action.BarrackGetOffensiveRankAction;
import byCodeGame.game.moudle.barrack.action.BarrackShowArmySkillAction;
import byCodeGame.game.moudle.barrack.action.BarrackShowMakeExploitAction;
import byCodeGame.game.moudle.barrack.action.BarrackShowVisitOffensiveAction;
import byCodeGame.game.moudle.barrack.action.BarrackUpArmySkillAction;
import byCodeGame.game.moudle.barrack.action.BarrackVisitOffensiveAction;
import byCodeGame.game.moudle.barrack.action.BuildBarrackAction;
import byCodeGame.game.moudle.barrack.action.ChangeBarrackAction;
import byCodeGame.game.moudle.barrack.action.GetBarrackDataAction;
import byCodeGame.game.moudle.barrack.action.GetTroopsDataAction;
import byCodeGame.game.moudle.barrack.action.RecruitArmsAction;
import byCodeGame.game.moudle.barrack.action.SellBarrackAction;
import byCodeGame.game.moudle.barrack.action.UpBarrackAction;
import byCodeGame.game.moudle.chapter.action.GetAllChapterDataAction;
import byCodeGame.game.moudle.chapter.action.GetAllStarAction;
import byCodeGame.game.moudle.chapter.action.GetChapterAwardDataAction;
import byCodeGame.game.moudle.chapter.action.GetChapterDataAction;
import byCodeGame.game.moudle.chapter.action.GetChapterStarsAwardAction;
import byCodeGame.game.moudle.chapter.action.GetPartChapterDataAction;
import byCodeGame.game.moudle.chapter.action.RaidsChapterAction;
import byCodeGame.game.moudle.chapter.action.RefreshChapterTimesAction;
import byCodeGame.game.moudle.chat.action.ChatAction;
import byCodeGame.game.moudle.chat.action.ChatCountryAction;
import byCodeGame.game.moudle.chat.action.ChatLegionAction;
import byCodeGame.game.moudle.chat.action.ChatPrivateAction;
import byCodeGame.game.moudle.city.action.CanUseArmyMineAction;
import byCodeGame.game.moudle.city.action.ChangeCoreInfoAction;
import byCodeGame.game.moudle.city.action.ChangeDefInfoAction;
import byCodeGame.game.moudle.city.action.ChangeWorldArmyFormationAction;
import byCodeGame.game.moudle.city.action.CityClearPumHistoryAction;
import byCodeGame.game.moudle.city.action.CityDismissArmyAction;
import byCodeGame.game.moudle.city.action.CityGetContributeRankAction;
import byCodeGame.game.moudle.city.action.CityGetMyLordInfoAction;
import byCodeGame.game.moudle.city.action.CityGoToVillageAction;
import byCodeGame.game.moudle.city.action.CityLeaveDefAction;
import byCodeGame.game.moudle.city.action.CityMarchWorldArmyAction;
import byCodeGame.game.moudle.city.action.CityMarchWorldArmyFromCityAction;
import byCodeGame.game.moudle.city.action.CityRetreateWorldMarchAction;
import byCodeGame.game.moudle.city.action.CitySetStrongHoldAction;
import byCodeGame.game.moudle.city.action.CityShowLegionCityAction;
import byCodeGame.game.moudle.city.action.CityShowOneWorldMarchAction;
import byCodeGame.game.moudle.city.action.CityShowSpeedUpWorldArmyAliveAction;
import byCodeGame.game.moudle.city.action.CityShowSpeedUpWorldMarchAction;
import byCodeGame.game.moudle.city.action.CityShowStayWorldArmyCityAction;
import byCodeGame.game.moudle.city.action.CityShowStrongHoldAction;
import byCodeGame.game.moudle.city.action.CityShowWorldCityInfoAction;
import byCodeGame.game.moudle.city.action.CityShowWorldCityWallInfoAction;
import byCodeGame.game.moudle.city.action.CityShowWorldMarchInfoAction;
import byCodeGame.game.moudle.city.action.CityShowWorldMarchRoleInfoAction;
import byCodeGame.game.moudle.city.action.CitySpeedUpWorldArmyAliveAction;
import byCodeGame.game.moudle.city.action.CitySpeedUpWorldMarchAction;
import byCodeGame.game.moudle.city.action.ConquerAction;
import byCodeGame.game.moudle.city.action.CrazyCityMineFarmAction;
import byCodeGame.game.moudle.city.action.DropCityMineOrFarmAction;
import byCodeGame.game.moudle.city.action.DropVassalAction;
import byCodeGame.game.moudle.city.action.GetAllArmyCityAction;
import byCodeGame.game.moudle.city.action.GetAllWorldFormationAction;
import byCodeGame.game.moudle.city.action.GetCityInfoAction;
import byCodeGame.game.moudle.city.action.GetCityMineFarmAction;
import byCodeGame.game.moudle.city.action.GetCityMineFarmInfoAction;
import byCodeGame.game.moudle.city.action.GetFiefInfoAction;
import byCodeGame.game.moudle.city.action.GetHeroLocationAction;
import byCodeGame.game.moudle.city.action.GetRankAwardAction;
import byCodeGame.game.moudle.city.action.GetRankInfoAction;
import byCodeGame.game.moudle.city.action.GetVassalInfoAction;
import byCodeGame.game.moudle.city.action.GetWroldFormationAction;
import byCodeGame.game.moudle.city.action.GoToCityAction;
import byCodeGame.game.moudle.city.action.JoinCityAction;
import byCodeGame.game.moudle.city.action.JoinCoreAction;
import byCodeGame.game.moudle.city.action.LeaveCoreAction;
import byCodeGame.game.moudle.city.action.OccupyCityMineOrFarmAction;
import byCodeGame.game.moudle.city.action.PvpFightAction;
import byCodeGame.game.moudle.city.action.SaveWorldFormationAction;
import byCodeGame.game.moudle.city.action.StartWorldFormationAction;
import byCodeGame.game.moudle.city.action.UpGradeRankAction;
import byCodeGame.game.moudle.city.action.WorldInfoAction;
import byCodeGame.game.moudle.fight.action.GetReportAction;
import byCodeGame.game.moudle.fight.action.RequestAttackChapterAction;
import byCodeGame.game.moudle.friend.action.GetFriendInfoAction;
import byCodeGame.game.moudle.friend.action.ModifyBlackListAction;
import byCodeGame.game.moudle.friend.action.ModifyFriendListAction;
import byCodeGame.game.moudle.gm.action.GMListAction;
import byCodeGame.game.moudle.gm.action.GetOnLineRoleDataAction;
import byCodeGame.game.moudle.gm.action.StopAppAction;
import byCodeGame.game.moudle.gm.action.UseKeyAction;
import byCodeGame.game.moudle.heart.action.BuyArmyTokenAction;
import byCodeGame.game.moudle.heart.action.HeartAction;
import byCodeGame.game.moudle.hero.action.AddArmsNumAction;
import byCodeGame.game.moudle.hero.action.DeployArmsAction;
import byCodeGame.game.moudle.hero.action.GetAllHeroArmsInfoAction;
import byCodeGame.game.moudle.hero.action.GetFormationDataAction;
import byCodeGame.game.moudle.hero.action.GetFormationDataAllAction;
import byCodeGame.game.moudle.hero.action.GetHeroArmyInfoAction;
import byCodeGame.game.moudle.hero.action.GetHeroListAction;
import byCodeGame.game.moudle.hero.action.GetRebLvInfoAction;
import byCodeGame.game.moudle.hero.action.HeroBuyManualAction;
import byCodeGame.game.moudle.hero.action.HeroCompoundHeroAction;
import byCodeGame.game.moudle.hero.action.HeroInsteadArmyAction;
import byCodeGame.game.moudle.hero.action.HeroRetrainArmyAction;
import byCodeGame.game.moudle.hero.action.HeroShowBuyManualAction;
import byCodeGame.game.moudle.hero.action.HeroShowDutyAction;
import byCodeGame.game.moudle.hero.action.HeroShowHeroDetailAction;
import byCodeGame.game.moudle.hero.action.HeroShowRankAction;
import byCodeGame.game.moudle.hero.action.HeroShowRetrainAction;
import byCodeGame.game.moudle.hero.action.HeroUpDutyLvAction;
import byCodeGame.game.moudle.hero.action.HeroUpRankLvAction;
import byCodeGame.game.moudle.hero.action.HeroUpdateSkillLvAction;
import byCodeGame.game.moudle.hero.action.QuickTrainHeroAction;
import byCodeGame.game.moudle.hero.action.SaveFormationAction;
import byCodeGame.game.moudle.hero.action.UpHeroRebirthAction;
import byCodeGame.game.moudle.hero.action.UpdateUseFormationIDAction;
import byCodeGame.game.moudle.inCome.action.AttachHeroAction;
import byCodeGame.game.moudle.inCome.action.BuildTypeLvUpAction;
import byCodeGame.game.moudle.inCome.action.CancelAttachAction;
import byCodeGame.game.moudle.inCome.action.ClearBuildQueueTimeAction;
import byCodeGame.game.moudle.inCome.action.FillPopulationAction;
import byCodeGame.game.moudle.inCome.action.GetBuildDataAction;
import byCodeGame.game.moudle.inCome.action.GetBuildInfoAction;
import byCodeGame.game.moudle.inCome.action.GetBuildQueueDataAction;
import byCodeGame.game.moudle.inCome.action.GetFightInfoAction;
import byCodeGame.game.moudle.inCome.action.GetIncomeDataAction;
import byCodeGame.game.moudle.inCome.action.GetInfoAttach;
import byCodeGame.game.moudle.inCome.action.GetNeedGoldAction;
import byCodeGame.game.moudle.inCome.action.GetNumResourceAction;
import byCodeGame.game.moudle.inCome.action.GetSimpleBuildDataAction;
import byCodeGame.game.moudle.inCome.action.InComeGetPrestigeAction;
import byCodeGame.game.moudle.inCome.action.InComeShowLevyInfoAction;
import byCodeGame.game.moudle.inCome.action.InComeShowRankInfoAction;
import byCodeGame.game.moudle.inCome.action.InComeShowSpeedUpLevyAction;
import byCodeGame.game.moudle.inCome.action.InComeSpeedUpLevyAction;
import byCodeGame.game.moudle.inCome.action.LevyAction;
import byCodeGame.game.moudle.inCome.action.OpenBuildAction;
import byCodeGame.game.moudle.inCome.action.OpenNewBuildQueueAction;
import byCodeGame.game.moudle.inCome.action.UpBuildAction;
import byCodeGame.game.moudle.legion.action.AddMaxPeopleNumAction;
import byCodeGame.game.moudle.legion.action.AgreeJoinLegionAction;
import byCodeGame.game.moudle.legion.action.ApplyJoinLegionAction;
import byCodeGame.game.moudle.legion.action.AutoAgreeJoinAction;
import byCodeGame.game.moudle.legion.action.CancelApplyAction;
import byCodeGame.game.moudle.legion.action.ChangNoticeAction;
import byCodeGame.game.moudle.legion.action.ChangeFaceIdAction;
import byCodeGame.game.moudle.legion.action.ChangeShortNameAction;
import byCodeGame.game.moudle.legion.action.CreatLegionAction;
import byCodeGame.game.moudle.legion.action.DeputyLegionAction;
import byCodeGame.game.moudle.legion.action.ExchangeLegionAction;
import byCodeGame.game.moudle.legion.action.GetAllMemberAction;
import byCodeGame.game.moudle.legion.action.GetApplyListAction;
import byCodeGame.game.moudle.legion.action.GetInfoExchangeAction;
import byCodeGame.game.moudle.legion.action.GetLegionDataAction;
import byCodeGame.game.moudle.legion.action.GetLegionListAction;
import byCodeGame.game.moudle.legion.action.QuiteLegionAction;
import byCodeGame.game.moudle.legion.action.RejectJoinAction;
import byCodeGame.game.moudle.legion.action.SearchLegionAction;
import byCodeGame.game.moudle.legion.action.SetAppointScienceAction;
import byCodeGame.game.moudle.legion.action.SetCityAction;
import byCodeGame.game.moudle.legion.action.UpgradeScienceAction;
import byCodeGame.game.moudle.login.action.CreatRoleAction;
import byCodeGame.game.moudle.login.action.GetRoleDataAction;
import byCodeGame.game.moudle.login.action.LoginAction;
import byCodeGame.game.moudle.mail.action.DeleteMailAction;
import byCodeGame.game.moudle.mail.action.GetAllMailAction;
import byCodeGame.game.moudle.mail.action.MailAction;
import byCodeGame.game.moudle.mail.action.SetMailCheckedAction;
import byCodeGame.game.moudle.mail.action.TakeItemMailAction;
import byCodeGame.game.moudle.market.action.BuyAuctionAction;
import byCodeGame.game.moudle.market.action.BuyGoldMarketAction;
import byCodeGame.game.moudle.market.action.CancelAuctionAction;
import byCodeGame.game.moudle.market.action.GetGoldMarketInfoAction;
import byCodeGame.game.moudle.market.action.MarketBargainItemsAction;
import byCodeGame.game.moudle.market.action.MarketBuyAction;
import byCodeGame.game.moudle.market.action.MarketBuyWorldMarketItemsAction;
import byCodeGame.game.moudle.market.action.MarketFreshAction;
import byCodeGame.game.moudle.market.action.MarketGetInfo;
import byCodeGame.game.moudle.market.action.MarketRefreshPrestigeWorldMarketItemAction;
import byCodeGame.game.moudle.market.action.MarketSellAction;
import byCodeGame.game.moudle.market.action.MarketShowBargainItemsAction;
import byCodeGame.game.moudle.market.action.MarketShowWorldMarketItemsAction;
import byCodeGame.game.moudle.market.action.MarketUnlockPrestigeMarketLimitAction;
import byCodeGame.game.moudle.market.action.MarketVisitSaleAction;
import byCodeGame.game.moudle.market.action.SellAuctionAction;
import byCodeGame.game.moudle.market.action.ShowAuctionInfoAction;
import byCodeGame.game.moudle.market.action.ShowAuctionSelfAction;
import byCodeGame.game.moudle.nation.action.GetNumnationAction;
import byCodeGame.game.moudle.nation.action.NationAction;
import byCodeGame.game.moudle.prop.action.AddMaxBagNumAction;
import byCodeGame.game.moudle.prop.action.ChangePrefixAction;
import byCodeGame.game.moudle.prop.action.EquipPropAction;
import byCodeGame.game.moudle.prop.action.GetPropListAction;
import byCodeGame.game.moudle.prop.action.PropGetIronAction;
import byCodeGame.game.moudle.prop.action.PropGetVisitTreasureAction;
import byCodeGame.game.moudle.prop.action.PropMakeIronAction;
import byCodeGame.game.moudle.prop.action.PropShowAddIronStrengthenAction;
import byCodeGame.game.moudle.prop.action.PropShowEquipAction;
import byCodeGame.game.moudle.prop.action.PropShowMakeIronAction;
import byCodeGame.game.moudle.prop.action.PropShowStrengthenAction;
import byCodeGame.game.moudle.prop.action.PropVisitTreasureAction;
import byCodeGame.game.moudle.prop.action.QuickDressAction;
import byCodeGame.game.moudle.prop.action.RecoveryEquipAction;
import byCodeGame.game.moudle.prop.action.RefineEquipAction;
import byCodeGame.game.moudle.prop.action.SingleDressAction;
import byCodeGame.game.moudle.prop.action.StrengthenEquipAction;
import byCodeGame.game.moudle.prop.action.UninstallEquipAction;
import byCodeGame.game.moudle.prop.action.UsePropAction;
import byCodeGame.game.moudle.pub.action.GetMapInfoAction;
import byCodeGame.game.moudle.pub.action.GetVisitAwardAction;
import byCodeGame.game.moudle.pub.action.PubChangeDeskHeroAction;
import byCodeGame.game.moudle.pub.action.PubDrinkWineAction;
import byCodeGame.game.moudle.pub.action.PubEnterAction;
import byCodeGame.game.moudle.pub.action.PubGetInfoAction;
import byCodeGame.game.moudle.pub.action.PubGetMileHerosAction;
import byCodeGame.game.moudle.pub.action.PubGetWineAction;
import byCodeGame.game.moudle.pub.action.PubHeroDequeueAction;
import byCodeGame.game.moudle.pub.action.PubMakeWineAction;
import byCodeGame.game.moudle.pub.action.PubMileConvertHeroAction;
import byCodeGame.game.moudle.pub.action.PubMultipleTalkAction;
import byCodeGame.game.moudle.pub.action.PubShowMakeWineAction;
import byCodeGame.game.moudle.pub.action.PubShowWineDeskAction;
import byCodeGame.game.moudle.pub.action.PubSpeedUpAction;
import byCodeGame.game.moudle.pub.action.PubTalkAction;
import byCodeGame.game.moudle.pub.action.PubUnlockWineDeskAction;
import byCodeGame.game.moudle.pub.action.PubVisitAction;
import byCodeGame.game.moudle.raid.action.CreateNewRoomAction;
import byCodeGame.game.moudle.raid.action.GetLobbyInfoAction;
import byCodeGame.game.moudle.raid.action.GetRoomInfoAction;
import byCodeGame.game.moudle.raid.action.JoinRoomAction;
import byCodeGame.game.moudle.raid.action.KickRoleAction;
import byCodeGame.game.moudle.raid.action.QuitRoomAction;
import byCodeGame.game.moudle.raid.action.SaveRoomRoleFormationAction;
import byCodeGame.game.moudle.raid.action.SetLoadingProgressAction;
import byCodeGame.game.moudle.raid.action.SetRoleStatusAction;
import byCodeGame.game.moudle.raid.action.StartBattleAction;
import byCodeGame.game.moudle.raid.action.UpdateLoadingAction;
import byCodeGame.game.moudle.role.action.FinishNewBuildAction;
import byCodeGame.game.moudle.role.action.FriendTipAction;
import byCodeGame.game.moudle.role.action.RoleGetLvDataAction;
import byCodeGame.game.moudle.role.action.RoleLvUpAction;
//import byCodeGame.game.moudle.raid.action.ChangeHeroAction;
//import byCodeGame.game.moudle.raid.action.CreateNewRoomAction;
//import byCodeGame.game.moudle.raid.action.GetLobbyInfoAction;
//import byCodeGame.game.moudle.raid.action.GetRoomInfoAction;
//import byCodeGame.game.moudle.raid.action.JoinRoomAction;
//import byCodeGame.game.moudle.raid.action.KickRoleAction;
//import byCodeGame.game.moudle.raid.action.QuitRoomAction;
//import byCodeGame.game.moudle.raid.action.ReadyBattleAction;
//import byCodeGame.game.moudle.raid.action.SaveRoomRoleFormationAction;
//import byCodeGame.game.moudle.raid.action.SetLoadingProgressAction;
//import byCodeGame.game.moudle.raid.action.SetRoleStatusAction;
//import byCodeGame.game.moudle.raid.action.StartBattleAction;
//import byCodeGame.game.moudle.raid.action.UpdateLoadingAction;
import byCodeGame.game.moudle.role.action.SetRoleFaceIdAction;
import byCodeGame.game.moudle.role.action.SetRoleLeadAction;
import byCodeGame.game.moudle.role.action.SetRoleNameAction;
import byCodeGame.game.moudle.science.action.ClearRoleScienceQueueTimeAction;
import byCodeGame.game.moudle.science.action.GetRoleScienceInfoAction;
import byCodeGame.game.moudle.science.action.GetRoleScienceQueueAction;
import byCodeGame.game.moudle.science.action.ScienceGetOfferScienceAwardAction;
import byCodeGame.game.moudle.science.action.ScienceOfferScienceInfoAction;
import byCodeGame.game.moudle.science.action.UpgradRoleScienceAction;
import byCodeGame.game.moudle.sign.action.GetSignAwardAction;
import byCodeGame.game.moudle.sign.action.GetSignHeroAction;
import byCodeGame.game.moudle.sign.action.GetSignInfoAciton;
import byCodeGame.game.moudle.sign.action.SignRetroactiveAction;
import byCodeGame.game.moudle.sign.action.SignTodayAction;
import byCodeGame.game.moudle.target.action.GetActiveAwardAction;
import byCodeGame.game.moudle.target.action.GetAwardTargetAction;
import byCodeGame.game.moudle.target.action.GetTargetInfoAction;
import byCodeGame.game.moudle.task.action.CompleteTaskAction;
import byCodeGame.game.moudle.task.action.GetDailyTaskAction;
import byCodeGame.game.moudle.task.action.GetDailyTaskAwardAction;
import byCodeGame.game.moudle.task.action.GetDoingTaskAction;
import byCodeGame.game.moudle.task.action.QuickCompleteDailyTaskAction;
import byCodeGame.game.moudle.test.action.TestFightAction;
import byCodeGame.game.moudle.vip.action.GetVipInfoAction;
import byCodeGame.game.moudle.vip.action.GetVipRawardAction;
import byCodeGame.game.moudle.vip.action.GetVipRawardInfoAction;
import byCodeGame.game.moudle.vip.action.RechargeAction;

/**
 * 通信指令导航
 * 
 */
public class Navigation {

	private static LoginAction loginAction;
	private static CreatRoleAction creatRoleAction;
	private static GetRoleDataAction getRoleDataAction;
	private static ChatAction chatAction;
	private static ChatPrivateAction chatPrivateAction;
	private static ChatCountryAction chatCountryAction;
	private static ChatLegionAction chatLegionAction;

	// 经营
	private static GetNeedGoldAction getNeedGoldAction;
	private static LevyAction levyAction;
	private static UpBuildAction upBuildAction;
	private static GetIncomeDataAction getIncomeDataAction;
	private static OpenBuildAction openBuildAction;
	private static GetBuildDataAction getBuildDataAction;
	private static GetBuildQueueDataAction getBuildQueueDataAction;
	private static ClearBuildQueueTimeAction clearBuildQueueTimeAction;
	private static OpenNewBuildQueueAction openNewBuildQueueAction;
	private static FillPopulationAction fillPopulationAction;
	private static AttachHeroAction attachHeroAction;
	private static CancelAttachAction cancelAttachAction;
	private static GetInfoAttach getInfoAttach;
	private static GetSimpleBuildDataAction getSimpleBuildDataAction;
	private static BuildTypeLvUpAction buildTypeLvUpAction;
	private static GetNumResourceAction getNumResourceAction;
	private static GetFightInfoAction getFightInfoAction;
	private static InComeShowSpeedUpLevyAction inComeShowSpeedUpLevyAction;
	private static InComeSpeedUpLevyAction inComeSpeedUpLevyAction;
	private static InComeGetPrestigeAction inComeGetPrestigeAction;
	private static InComeShowRankInfoAction inComeShowRankInfoAction;
	private static GetBuildInfoAction getBuildInfoAction;
	private static InComeShowLevyInfoAction inComeShowLevyInfoAction;
	

	private static GetOnLineRoleDataAction getOnLineRoleDataAction;
	private static GMListAction gmListAction;
	private static StopAppAction stopAppAction;
	private static UseKeyAction useKeyAction;
	
	
	private static GetDoingTaskAction getDoingTaskAction;
	private static CompleteTaskAction completeTaskAction;
	private static GetDailyTaskAwardAction getDailyTaskAwardAction;
	private static QuickCompleteDailyTaskAction quickCompleteDailyTaskAction;
	private static GetDailyTaskAction getDailyTaskAction;
	private static NationAction nationAction;
	private static GetNumnationAction getNumnationAction;
	private static MailAction mailAction;
	private static CreatLegionAction creatLegionAction;
	private static GetLegionDataAction getLegionDataAction;
	private static GetLegionListAction getLegionListAction;
	private static ApplyJoinLegionAction applyJoinLegionAction;
	private static GetApplyListAction getApplyListAction;
	private static AgreeJoinLegionAction agreeJoinLegionAction;
	private static GetAllMailAction getAllMailAction;
	private static TakeItemMailAction takeItemMailAction;
	private static DeleteMailAction deleteMailAction;
	private static SetMailCheckedAction setMailCheckedAction;
	private static DeputyLegionAction deputyLegionAction;
	private static GetPropListAction getPropListAction;
	private static ChangeFaceIdAction changeFaceIdAction;
	private static ChangNoticeAction changNoticeAction;
	private static GetAllMemberAction getAllMemberAction;
	private static QuiteLegionAction quiteLegionAction;
	private static SearchLegionAction searchLegionAction;
	private static UpgradeScienceAction upgradeScienceAction;
	private static SetAppointScienceAction setAppointScienceAction;
	private static AutoAgreeJoinAction autoAgreeJoinAction;
	private static ExchangeLegionAction exchangeLegionAction;
	private static RejectJoinAction rejectJoinAction;
	private static CancelApplyAction cancelApplyAction;
	private static AddMaxPeopleNumAction addMaxPeopleNumAction;
	private static GetInfoExchangeAction getInfoExchangeAction;
	private static SetCityAction setCityAction;
	private static ChangeShortNameAction changeShortNameAction;

	private static UsePropAction usePropAction;
	private static EquipPropAction equipPropAction;
	private static UninstallEquipAction uninstallEquipAction;
	private static StrengthenEquipAction strengthenEquipAction;
	private static RecoveryEquipAction recoveryEquipAction;
	private static AddMaxBagNumAction addMaxBagNumAction;
	private static RefineEquipAction refineEquipAction;
	private static ChangePrefixAction changePrefixAction;
	private static PropMakeIronAction propMakeIronAction;
	private static PropShowMakeIronAction propShowMakeIronAction;
	private static PropGetIronAction propGetIronAction;
	private static PropGetVisitTreasureAction propGetVisitTreasureAction;
	private static PropVisitTreasureAction propVisitTreasureAction;
	private static PropShowStrengthenAction propShowStrengthenAction;
	private static PropShowEquipAction propShowEquipAction;
	private static PropShowAddIronStrengthenAction propShowAddIronStrengthenAction;

	private static GetGoldMarketInfoAction getGoldMarketInfoAction;
	private static BuyGoldMarketAction buyGoldMarketAction;
	private static ShowAuctionInfoAction showAuctionInfoAction;
	private static SellAuctionAction sellAuctionAction;
	private static BuyAuctionAction buyAuctionAction;
	private static CancelAuctionAction cancelAuctionAction;
	private static MarketVisitSaleAction marketVisitSaleAction;
	private static MarketBargainItemsAction marketBargainItemsAction;
	private static MarketShowBargainItemsAction marketShowBargainItemsAction;
	private static ShowAuctionSelfAction showAuctionSelfAction;
	private static MarketSellAction marketSellAction;
	private static MarketBuyAction marketBuyAction;
	private static MarketFreshAction marketFreshAction;
	private static MarketGetInfo marketGetInfo;
	private static MarketShowWorldMarketItemsAction marketShowWorldMarketItemsAction;
	private static MarketBuyWorldMarketItemsAction marketBuyWorldMarketItemsAction;
	private static MarketRefreshPrestigeWorldMarketItemAction marketRefreshPrestigeWorldMarketItemAction;	
	private static MarketUnlockPrestigeMarketLimitAction marketUnlockPrestigeMarketLimitAction;
	
	// 武将
	private static GetHeroListAction getHeroListAction;
	private static QuickDressAction quickDressAction;
	private static SingleDressAction singleDressAction;
	private static QuickTrainHeroAction quickTrainHeroAction;
	private static SaveFormationAction saveFormationAction;
	private static GetFormationDataAction getFormationDataAction;
	private static GetFormationDataAllAction getFormationDataAllAction;
	private static UpdateUseFormationIDAction updateUseFormationIDAction;
	private static UpHeroRebirthAction upHeroRebirthAction;
	private static GetHeroArmyInfoAction getHeroArmyInfoAction;
	private static GetRebLvInfoAction getRebLvInfoAction;
	private static HeroUpdateSkillLvAction heroUpdateSkillLvAction;
	private static GetAllHeroArmsInfoAction getAllHeroArmsInfoAction;
	private static HeroRetrainArmyAction heroRetrainArmyAction;
	private static HeroUpDutyLvAction heroUpDutyLvAction;
	private static HeroUpRankLvAction heroUpRankLvAction;
	private static HeroInsteadArmyAction heroInsteadArmyAction;
	private static HeroShowDutyAction heroShowDutyAction;
	private static HeroShowRankAction heroShowRankAction;
	private static HeroShowHeroDetailAction heroShowHeroDetailAction;
	private static HeroShowRetrainAction heroShowRetrainAction;
	private static HeroCompoundHeroAction heroCompoundHeroAction;
	private static HeroShowBuyManualAction heroShowBuyManualAction;
	private static HeroBuyManualAction heroBuyManualAction;

	// 酒馆
	private static PubTalkAction pubTalkAction;
	private static PubVisitAction pubVisitAction;
	private static GetVisitAwardAction getVisitAwardAction;
	private static GetMapInfoAction getMapInfoAction;
	private static PubHeroDequeueAction pubHeroDequeueAction;
	private static PubGetInfoAction pubGetInfoAction;
	private static PubMultipleTalkAction pubMultipleTalkAction;
	private static PubEnterAction pubEnterAction;
	private static PubMileConvertHeroAction pubMileConvertHeroAction;
	private static PubGetMileHerosAction pubGetMileHerosAction;
	private static PubShowWineDeskAction pubShowWineDeskAction;
	private static PubChangeDeskHeroAction pubChangeDeskHeroAction;
	private static PubShowMakeWineAction pubShowMakeWineAction;
	private static PubDrinkWineAction pubDrinkWineAction;
	private static PubMakeWineAction pubMakeWineAction;
	private static PubGetWineAction pubGetWineAction;
	private static PubUnlockWineDeskAction pubUnlockWineDeskAction;
	private static PubSpeedUpAction pubSpeedUpAction;

	private static BuildBarrackAction buildBarrackAction;
	private static UpBarrackAction upBarrackAction;
	private static SellBarrackAction sellBarrackAction;
	private static ChangeBarrackAction changeBarrackAction;
	private static RecruitArmsAction recruitArmsAction;
	private static DeployArmsAction deployArmsAction;
	private static AddArmsNumAction addArmsNumAction;

	private static HeartAction heartAction;
	private static BuyArmyTokenAction buyArmyTokenAction;
	private static RechargeAction rechargeAction;
	private static GetVipInfoAction getVipInfoAction;
	private static GetVipRawardInfoAction getVipRawardInfoAction;
	private static GetVipRawardAction getVipRawardAction;

	// 兵营
	private static GetBarrackDataAction getBarrackDataAction;
	private static GetTroopsDataAction getTroopsDataAction;
	private static GetRoleScienceInfoAction getRoleScienceInfoAction;
	private static UpgradRoleScienceAction upgradRoleScienceAction;
	private static GetRoleScienceQueueAction getRoleScienceQueueAction;
	private static ClearRoleScienceQueueTimeAction clearRoleScienceQueueTimeAction;
	private static ScienceOfferScienceInfoAction scienceOfferScienceInfoAction;
	private static ScienceGetOfferScienceAwardAction scienceGetOfferScienceAwardAction;
	private static BarrackShowMakeExploitAction barrackShowMakeExploitAction;
	private static BarrackGetExploitAction barrackGetExploitAction;
	private static BarrackUpArmySkillAction barrackUpArmySkillAction;
	private static BarrackVisitOffensiveAction barrackVisitOffensiveAction;
	private static BarrackShowArmySkillAction barrackShowArmySkillAction;
	private static BarrackShowVisitOffensiveAction barrackShowVisitOffensiveAction;
	private static BarrackGetOffensiveRankAction barrackGetOffensiveRankAction;

	private static GetChapterDataAction getChapterDataAction;
	private static RequestAttackChapterAction requestAttackChapterAction;
	private static GetReportAction getReportAction;
	private static GetAllChapterDataAction getAllChapterDataAction;
	private static GetChapterStarsAwardAction getChapterStarsAwardAction;
	private static GetAllStarAction getAllStarAction;
	private static GetPartChapterDataAction getPartChapterDataAction;

	private static GetFriendInfoAction getFriendInfoAction;
	private static ModifyFriendListAction modifyFriendListAction;
	private static ModifyBlackListAction modifyBlackListAction;
	private static SetRoleNameAction setRoleNameAction;
	private static SetRoleFaceIdAction setRoleFaceIdAction;
	private static RoleLvUpAction roleLvUpAction;
	private static RoleGetLvDataAction roleGetLvDataAction;
	private static SetRoleLeadAction setRoleLeadAction;
	private static FinishNewBuildAction finishNewBuildAction;
	private static FriendTipAction friendTipAction;

	private static GetSignInfoAciton getSignInfoAciton;
	private static SignTodayAction signTodayAction;
	private static SignRetroactiveAction signRetroactiveAction;
	private static GetSignAwardAction getSignAwardAction;
	private static GetSignHeroAction getSignHeroAction;

	private static RaidsChapterAction raidsChapterAction;
	private static RefreshChapterTimesAction refreshChapterTimesAction;
	private static EatAnimalAction eatAnimalAction;
	private static GetAnimalInfoAction getAnimalInfoAction;
	private static GetChapterAwardDataAction getChapterAwardDataAction;

	private static GetArenaDataAction getArenaDataAction;
	private static GetArenaTargetAction getArenaTargetAction;
	private static DareTargetAction dareTargetAction;
	private static SetFightHeroAction setFightHeroAction;
	private static TestArenaFightAction testArenaFightAction;
	private static GetFightLoadAction getFightLoadAction;
	private static GetLadderAction getLadderAction;
	private static GetNowLadderAction getNowLadderAction;

	private static GetLobbyInfoAction getLobbyInfoAction;
	private static CreateNewRoomAction createNewRoomAction;
	private static JoinRoomAction joinRoomAction;
	private static GetRoomInfoAction getRoomInfoAction;
	private static QuitRoomAction quitRoomAction;
	private static KickRoleAction kickRoleAction;
	private static SaveRoomRoleFormationAction saveRoomRoleFormationAction;
	private static SetRoleStatusAction setRoleStatusAction;
	private static StartBattleAction startBattleAction;
	private static SetLoadingProgressAction setLoadingProgressAction;
	private static UpdateLoadingAction updateLoadingAction;
	private static GetCityInfoAction getCityInfoAction;
	private static GoToCityAction goToCityAction;
	private static JoinCityAction joinCityAction;
	private static WorldInfoAction worldInfoAction;
	private static GetFiefInfoAction getFiefInfoAction;
	private static GetCityMineFarmAction getCityMineFarmAction;
	private static GetCityMineFarmInfoAction getCityMineFarmInfoAction;
	private static OccupyCityMineOrFarmAction occupyCityMineOrFarmAction;
	private static CanUseArmyMineAction canUseArmyMineAction;
	private static CrazyCityMineFarmAction crazyCityMineFarmAction;
	private static DropCityMineOrFarmAction dropCityMineOrFarmAction;
	private static ConquerAction conquerAction;
	private static DropVassalAction dropVassalAction;
	private static StartWorldFormationAction startWorldFormationAction;
	private static UpGradeRankAction upGradeRankAction;
	private static PvpFightAction pvpFightAction;
	private static GetRankInfoAction getRankInfoAction;
	private static GetRankAwardAction getRankAwardAction;
	private static GetVassalInfoAction GetVassalInfoAction;
	private static GetAllWorldFormationAction getAllWorldFormationAction;
	private static SaveWorldFormationAction saveWorldFormationAction;
	private static GetHeroLocationAction getHeroLocationAction;
	private static GetWroldFormationAction getWroldFormationAction;
	private static ChangeWorldArmyFormationAction changeWorldArmyFormationAction;
	private static CityShowWorldCityWallInfoAction cityShowWorldCityWallInfoAction;
	private static CityMarchWorldArmyAction cityMarchWorldArmyAction;
	private static CityShowSpeedUpWorldMarchAction cityShowSpeedUpWorldMarchAction;
	private static CitySpeedUpWorldMarchAction citySpeedUpWorldMarchAction;
	private static CityShowStayWorldArmyCityAction cityShowStayWorldArmyCityAction;
	private static ChangeDefInfoAction changeDefInfoAction;
	private static ChangeCoreInfoAction changeCoreInfoAction;
	private static JoinCoreAction joinCoreAction;
	private static LeaveCoreAction leaveCoreAction;
	private static CityShowWorldMarchInfoAction cityShowWorldMarchInfoAction;
	private static CityShowWorldCityInfoAction cityShowWorldCityInfoAction;
	private static CityGetContributeRankAction cityGetContributeRankAction;
	private static CityMarchWorldArmyFromCityAction cityMarchWorldArmyFromCityAction;
	private static CityShowWorldMarchRoleInfoAction cityShowWorldMarchRoleInfoAction;
	private static CityShowOneWorldMarchAction cityShowOneWorldMarchAction;
	private static CityRetreateWorldMarchAction cityRetreateWorldMarchAction;
	private static CityShowStrongHoldAction cityShowStrongHoldAction;
	private static CitySetStrongHoldAction citySetStrongHoldAction;
	private static CityDismissArmyAction cityDismissArmyAction;
	private static GetAllArmyCityAction getAllArmyCityAction;
	private static CityLeaveDefAction cityLeaveDefAction;
	private static CitySpeedUpWorldArmyAliveAction citySpeedUpWorldArmyAliveAction;
	private static CityShowSpeedUpWorldArmyAliveAction cityShowSpeedUpWorldArmyAliveAction;
	private static CityGetMyLordInfoAction cityGetMyLordInfoAction;
	private static CityClearPumHistoryAction cityClearPumHistoryAction;
	private static CityGoToVillageAction cityGoToVillageAction;
	private static CityShowLegionCityAction cityShowLegionCityAction;

	// private static GetArmsResearchAction getArmsResearchAction;
	// private static AddArmsResearchLvAction addArmsResearchLvAction;
	// private static UpRoleArmyAction upRoleArmyAction;
	// private static ReSetRoleArmyAction reSetRoleArmyAction;
	// private static GetRoleArmyInfoAction getRoleArmyInfoAction;
	private static ArmsChangeHeroSoldierAction armsChangeHeroSoldierAction;
	private static ArmsUpdateHeroSoldierLvAction armsUpdateHeroSoldierLvAction;
	private static ArmsUpdateHeroSoldierQualityAction armsUpdateHeroSoldierQualityAction;
	private static ArmsUpdateHeroSoldierSkillAction armsUpdateHeroSoldierSkillAction;

	private static GetBabelInfoAction getBabelInfoAction;
	private static FightBabelAction fightBabelAction;
	private static ChoiceBabelNpcAction choiceBabelNpcAction;
	private static ChangeTroopDataAction changeTroopDataAction;
	private static ResetTowerAction resetTowerAction;
	private static ReviveHeroAction reviveHeroAction;
	private static GetHeroInfoAction getHeroInfoAction;
	
	private static GetTargetInfoAction getTargetInfoAction;
	private static GetAwardTargetAction getAwardTargetAction;
	private static GetActiveAwardAction getActiveAwardAction;
	
	private static ActivityShowActivityAction activityShowActivityAction;
	/************ 测试用 ******************/
	private static TestFightAction testFightAction;

	// 导航集合
	public static Map<Short, ActionSupport> navigate = new ConcurrentHashMap<Short, ActionSupport>();

	// 初始化导航
	public static void init() {

		navigate.put(NavigationModule.LOGIN, loginAction);
		navigate.put(NavigationModule.CREAT_ROLE, creatRoleAction);
		navigate.put(NavigationModule.GET_ROLE_DATA, getRoleDataAction);

		navigate.put(NavigationModule.CHAT, chatAction);
		navigate.put(NavigationModule.CHAT_PRIVATE, chatPrivateAction);
		navigate.put(NavigationModule.CHAT_COUNTRY, chatCountryAction);		
		navigate.put(NavigationModule.CHAT_LEGION, chatLegionAction);

		/*** --------------------------300+-------------------------------------- */
		navigate.put(NavigationModule.GET_NEED_GOLD, getNeedGoldAction);
		navigate.put(NavigationModule.LEVY, levyAction);
		navigate.put(NavigationModule.UP_BUILD, upBuildAction);
		navigate.put(NavigationModule.GET_INCOME_DATE, getIncomeDataAction);
		navigate.put(NavigationModule.OPEN_BUILD, openBuildAction);
		navigate.put(NavigationModule.GET_BUILD_DATA, getBuildDataAction);
		navigate.put(NavigationModule.GET_BUILD_QUEUE_DATA, getBuildQueueDataAction);
		navigate.put(NavigationModule.CLEAR_BUILD_QUEUE_TIME, clearBuildQueueTimeAction);
		navigate.put(NavigationModule.OPEN_NEW_BUILD_QUEUE, openNewBuildQueueAction);
		navigate.put(NavigationModule.FILL_POPULATION, fillPopulationAction);
		navigate.put(NavigationModule.ATTACH_HERO, attachHeroAction);
		navigate.put(NavigationModule.CANCEL_ATTACH, cancelAttachAction);
		navigate.put(NavigationModule.GET_INFO_ATTACH, getInfoAttach);
		navigate.put(NavigationModule.GET_SIMPLE_BUILD_DATA, getSimpleBuildDataAction);
		navigate.put(NavigationModule.BUILD_TYPE_LV_UP, buildTypeLvUpAction);
		navigate.put(NavigationModule.GET_NUM_RESOURCE, getNumResourceAction);
		navigate.put(NavigationModule.GET_FIGHT_INFO, getFightInfoAction);
		navigate.put(NavigationModule.SHOW_SPEED_UP_LEVY, inComeShowSpeedUpLevyAction);
		navigate.put(NavigationModule.SPEED_UP_LEVY, inComeSpeedUpLevyAction);
		navigate.put(NavigationModule.GET_PRESTIGE, inComeGetPrestigeAction);
		navigate.put(NavigationModule.SHOW_RANK_INFO, inComeShowRankInfoAction);
		navigate.put(NavigationModule.GET_BUILD_INFO, getBuildInfoAction);
		navigate.put(NavigationModule.SHOW_LEVY_INFO, inComeShowLevyInfoAction);		
		

		/** --------------------------------GM------------------------------ */
		navigate.put(NavigationModule.GET_ONLINE_ROLE_DATA, getOnLineRoleDataAction);
		navigate.put(NavigationModule.GM_LIST, gmListAction);
		navigate.put(NavigationModule.GM_STOP_APP, stopAppAction);
		navigate.put(NavigationModule.USE_PRIVILEGE, useKeyAction);
		
		navigate.put(NavigationModule.GET_DONG_TASK, getDoingTaskAction);
		navigate.put(NavigationModule.COMPLETE_TASK, completeTaskAction);
		navigate.put(NavigationModule.GET_DAILY_TASK_AWARD, getDailyTaskAwardAction);
		navigate.put(NavigationModule.QUICK_COMPLETE_DAILY_TASK, quickCompleteDailyTaskAction);
		navigate.put(NavigationModule.GET_DAILY_TASK, getDailyTaskAction);

		navigate.put(NavigationModule.SET_NATION, nationAction);
		navigate.put(NavigationModule.GET_NATION_NUM, getNumnationAction);

		navigate.put(NavigationModule.SEND_MAIL_PLAYER, mailAction);
		navigate.put(NavigationModule.GET_ALL_MAIL, getAllMailAction);
		navigate.put(NavigationModule.TAKE_ITEM_MAIL, takeItemMailAction);
		navigate.put(NavigationModule.DELETE_MAIL, deleteMailAction);
		navigate.put(NavigationModule.MAIL_CHECKED_TYPE, setMailCheckedAction);

		navigate.put(NavigationModule.CRETA_LEGION, creatLegionAction);
		navigate.put(NavigationModule.GET_LEGION_DATA, getLegionDataAction);
		navigate.put(NavigationModule.CHANG_FACE_ID, changeFaceIdAction);
		navigate.put(NavigationModule.CHANG_NAOTICE, changNoticeAction);
		navigate.put(NavigationModule.GET_ALL_MEMBER, getAllMemberAction);
		navigate.put(NavigationModule.GET_LEGION_LIST, getLegionListAction);
		navigate.put(NavigationModule.APPLY_JOIN_LEGION, applyJoinLegionAction);
		navigate.put(NavigationModule.GET_APPLY_LIST, getApplyListAction);
		navigate.put(NavigationModule.AGREE_JOIN_LEGION, agreeJoinLegionAction);
		navigate.put(NavigationModule.SET_DEPUTY, deputyLegionAction);
		navigate.put(NavigationModule.QUITE_LEGION, quiteLegionAction);
		navigate.put(NavigationModule.SEARCH_LEGION, searchLegionAction);
		navigate.put(NavigationModule.UPGRADE_SCIENCE, upgradeScienceAction);
		navigate.put(NavigationModule.SET_APPOINT_SCIENCE, setAppointScienceAction);
		navigate.put(NavigationModule.SET_AUTO_AGREE_JOIN, autoAgreeJoinAction);
		navigate.put(NavigationModule.EXCHANGE_LEGION, exchangeLegionAction);
		navigate.put(NavigationModule.REJECT_JOIN, rejectJoinAction);
		navigate.put(NavigationModule.CANCEL_APPLY, cancelApplyAction);
		navigate.put(NavigationModule.ADD_MAX_PEOPLE_NUM, addMaxPeopleNumAction);
		navigate.put(NavigationModule.GET_INFO_EXCHANGE, getInfoExchangeAction);
		navigate.put(NavigationModule.SET_TARGET_LEGION, setCityAction);
		navigate.put(NavigationModule.CHANG_SHORT_NAME, changeShortNameAction);
		/** -----------------------------900+------------------------------ */
		navigate.put(NavigationModule.GET_PORP_LIST, getPropListAction);
		navigate.put(NavigationModule.USE_PROP, usePropAction);
		navigate.put(NavigationModule.EQUIP_PROP, equipPropAction);
		navigate.put(NavigationModule.UNINSTALL_EQUIP, uninstallEquipAction);
		navigate.put(NavigationModule.QUICK_DRESS, quickDressAction);
		navigate.put(NavigationModule.SINGLE_DRESS, singleDressAction);
		navigate.put(NavigationModule.STRENGTHEN_EQUIP, strengthenEquipAction);
		navigate.put(NavigationModule.ADD_MAX_NUM_BACK, addMaxBagNumAction);
		navigate.put(NavigationModule.ADD_MAX_PEOPLE_NUM, addMaxPeopleNumAction);
		navigate.put(NavigationModule.GET_INFO_EXCHANGE, getInfoExchangeAction);

		navigate.put(NavigationModule.RECOVERY_EQUIP, recoveryEquipAction);
		navigate.put(NavigationModule.REFINE_EQUIP, refineEquipAction);
		navigate.put(NavigationModule.CHANGE_PREFIX, changePrefixAction);
		navigate.put(NavigationModule.MAKE_IRON, propMakeIronAction);
		navigate.put(NavigationModule.SHOW_MAKE_IRON, propShowMakeIronAction);
		navigate.put(NavigationModule.GET_IRON, propGetIronAction);
		navigate.put(NavigationModule.GET_VISIT_TREASURE, propGetVisitTreasureAction);
		navigate.put(NavigationModule.VISIT_TREASURE, propVisitTreasureAction);
		navigate.put(NavigationModule.SHOW_STRENGTHEN, propShowStrengthenAction);
		navigate.put(NavigationModule.SHOW_EQUIP, propShowEquipAction);
		navigate.put(NavigationModule.SHOW_ADD_IRON_STRENGTHEN,propShowAddIronStrengthenAction);
		/** -----------------------------1000+------------------------------ */
		navigate.put(NavigationModule.SELL_MARKET, marketSellAction);
		navigate.put(NavigationModule.BUY_MARKET, marketBuyAction);
		navigate.put(NavigationModule.FRESH_MARKET, marketFreshAction);
		navigate.put(NavigationModule.GET_MARKET_INFO, marketGetInfo);
		navigate.put(NavigationModule.GET_GOLD_MARKET_INFO, getGoldMarketInfoAction);
		navigate.put(NavigationModule.BUY_GOLD_MARKET, buyGoldMarketAction);
		navigate.put(NavigationModule.SHOW_AUCTION_INFO, showAuctionInfoAction);
		navigate.put(NavigationModule.SELL_AUCTION, sellAuctionAction);
		navigate.put(NavigationModule.BUY_AUCTION, buyAuctionAction);
		navigate.put(NavigationModule.CANCEL_AUCTION, cancelAuctionAction);
		navigate.put(NavigationModule.VISIT_SALE, marketVisitSaleAction);
		navigate.put(NavigationModule.BARGAIN_ITEMS, marketBargainItemsAction);
		navigate.put(NavigationModule.SHOW_BARGAIN_ITEMS, marketShowBargainItemsAction);
		navigate.put(NavigationModule.SHOW_AUCTION_SELF, showAuctionSelfAction);
		navigate.put(NavigationModule.SHOW_WORLD_MARKET_ITEMS, marketShowWorldMarketItemsAction);
		navigate.put(NavigationModule.BUY_WORLD_MARKET_ITEMS, marketBuyWorldMarketItemsAction);
		navigate.put(NavigationModule.REFRESH_WORLD_MARKET_ITEM, marketRefreshPrestigeWorldMarketItemAction);
		navigate.put(NavigationModule.UNLOCK_PRESTIGE_LIMIT, marketUnlockPrestigeMarketLimitAction);
		
		/** -----------------------------1100+------------------------------ */
		// 武将
		navigate.put(NavigationModule.GET_HERO_LIST, getHeroListAction);
		navigate.put(NavigationModule.QUICK_TRAIN_HERO, quickTrainHeroAction);
		navigate.put(NavigationModule.SAVE_FORMATION, saveFormationAction);
		navigate.put(NavigationModule.GET_FORMATION_DATA, getFormationDataAction);
		navigate.put(NavigationModule.UP_REBIRTH_LV, upHeroRebirthAction);
		navigate.put(NavigationModule.DEPLOY_ARMS, deployArmsAction);
		navigate.put(NavigationModule.ADD_ARMSNUM, addArmsNumAction);
		navigate.put(NavigationModule.GET_FORMATION_DATAALL, getFormationDataAllAction);
		navigate.put(NavigationModule.UPDATE_USEFORMATIONID, updateUseFormationIDAction);
		navigate.put(NavigationModule.GET_HERO_ARMY_INFO, getHeroArmyInfoAction);
		navigate.put(NavigationModule.GET_HERO_RLV_INFO, getRebLvInfoAction);
		navigate.put(NavigationModule.HERO_UPDATE_SKILL_LV, heroUpdateSkillLvAction);
		navigate.put(NavigationModule.GET_ALL_HERO_ARMY_INFO, getAllHeroArmsInfoAction);
		navigate.put(NavigationModule.RETRAIN_ARMY, heroRetrainArmyAction);
		navigate.put(NavigationModule.UP_DUTY_LV, heroUpDutyLvAction);
		navigate.put(NavigationModule.UP_RANK_LV, heroUpRankLvAction);
		navigate.put(NavigationModule.INSTEAD_ARMY, heroInsteadArmyAction);
		navigate.put(NavigationModule.SHOW_DUTY, heroShowDutyAction);
		navigate.put(NavigationModule.SHOW_RANK, heroShowRankAction);
		navigate.put(NavigationModule.SHOW_HERO_DETAIL, heroShowHeroDetailAction);
		navigate.put(NavigationModule.SHOW_RETRAIN, heroShowRetrainAction);
		navigate.put(NavigationModule.COMPOUND_HERO, heroCompoundHeroAction);
		navigate.put(NavigationModule.SHOW_BUY_MANUAL, heroShowBuyManualAction);
		navigate.put(NavigationModule.BUY_MANUAL, heroBuyManualAction);

		// 酒馆
		navigate.put(NavigationModule.TALK_PUB, pubTalkAction);
		navigate.put(NavigationModule.TALK_VISIT, pubVisitAction);
		navigate.put(NavigationModule.GET_VISIT_AWARD, getVisitAwardAction);
		navigate.put(NavigationModule.GET_MAPINFO, getMapInfoAction);
		navigate.put(NavigationModule.DEQUEUE_HERO_PUB, pubHeroDequeueAction);
		navigate.put(NavigationModule.GET_PUB_INFO, pubGetInfoAction);
		navigate.put(NavigationModule.MULTIPLE_TALK, pubMultipleTalkAction);
		navigate.put(NavigationModule.MILE_CONVERT_HERO, pubMileConvertHeroAction);
		navigate.put(NavigationModule.ENTER_PUB, pubEnterAction);
		navigate.put(NavigationModule.GET_PUB_HEROS, pubGetMileHerosAction);
		navigate.put(NavigationModule.SHOW_WINE_DESK, pubShowWineDeskAction);
		navigate.put(NavigationModule.CHANGE_DESK_HERO, pubChangeDeskHeroAction);
		navigate.put(NavigationModule.SHOW_MAKE_WINE, pubShowMakeWineAction);
		navigate.put(NavigationModule.DRINK_WINE, pubDrinkWineAction);
		navigate.put(NavigationModule.MAKE_WINE, pubMakeWineAction);
		navigate.put(NavigationModule.GET_WINE, pubGetWineAction);
		navigate.put(NavigationModule.UNLOCK_DESK, pubUnlockWineDeskAction);
		navigate.put(NavigationModule.SPEED_UP, pubSpeedUpAction);

		navigate.put(NavigationModule.BUILD_BARRACK, buildBarrackAction);
		navigate.put(NavigationModule.UP_BARRACK, upBarrackAction);
		navigate.put(NavigationModule.SELL_BARRACK, sellBarrackAction);
		navigate.put(NavigationModule.CHANGE_BARRACK, changeBarrackAction);
		navigate.put(NavigationModule.RECRUIT_ARMS, recruitArmsAction);
		navigate.put(NavigationModule.GET_BARRACK_DATA, getBarrackDataAction);
		navigate.put(NavigationModule.GET_TROOPS_DATA, getTroopsDataAction);
		navigate.put(NavigationModule.SHOW_MAKE_EXPLOIT, barrackShowMakeExploitAction);
		navigate.put(NavigationModule.GET_EXPLOIT, barrackGetExploitAction);
		navigate.put(NavigationModule.UP_ARMY_SKILL, barrackUpArmySkillAction);
		navigate.put(NavigationModule.VISIT_OFFENSIVE, barrackVisitOffensiveAction);
		navigate.put(NavigationModule.SHOW_ARMY_SKILL, barrackShowArmySkillAction);
		navigate.put(NavigationModule.SHOW_VISIT_OFFENSIVE, barrackShowVisitOffensiveAction);
		navigate.put(NavigationModule.GET_OFFENSIVE_RANK, barrackGetOffensiveRankAction);

		navigate.put(NavigationModule.GET_ROLE_SCIENCE_INFO, getRoleScienceInfoAction);
		navigate.put(NavigationModule.UPGRADE_ROLE_SCIENCE, upgradRoleScienceAction);
		navigate.put(NavigationModule.GET_ROLE_SCIENCE_QUEUE, getRoleScienceQueueAction);
		navigate.put(NavigationModule.CLEAR_ROLE_SCIENCE_QUEUE_TIME, clearRoleScienceQueueTimeAction);
		navigate.put(NavigationModule.OFFER_SCIENCE, scienceOfferScienceInfoAction);
		navigate.put(NavigationModule.GET_VISIT_SCIENCE_AWARD, scienceGetOfferScienceAwardAction);

		navigate.put(NavigationModule.HEART, heartAction);
		navigate.put(NavigationModule.BUY_ARMYTOKEN, buyArmyTokenAction);
		navigate.put(NavigationModule.RECHARGE, rechargeAction);
		navigate.put(NavigationModule.GET_VIP_INFO, getVipInfoAction);
		navigate.put(NavigationModule.GET_VIPRAWARD_INFO, getVipRawardInfoAction);
		navigate.put(NavigationModule.GET_VIP_AWARD, getVipRawardAction);
		navigate.put(NavigationModule.ROLE_CITY_LV_UP, roleLvUpAction);
		navigate.put(NavigationModule.ROLE_GET_LV_DATA, roleGetLvDataAction);

		navigate.put(NavigationModule.GET_CHAPTER_DATA, getChapterDataAction);
		navigate.put(NavigationModule.GET_ALL_CHAPTER_DATA, getAllChapterDataAction);
		navigate.put(NavigationModule.RAIDS_CHAPTER, raidsChapterAction);
		navigate.put(NavigationModule.GET_CHAPTER_AWARD, getChapterStarsAwardAction);
		navigate.put(NavigationModule.GET_ALL_STAR, getAllStarAction);
		navigate.put(NavigationModule.GET_CHAPTER_AWARD_DATA, getChapterAwardDataAction);
		navigate.put(NavigationModule.GET_PART_CHAPTER_DATA, getPartChapterDataAction);

		navigate.put(NavigationModule.REQUEST_ATK_CHAPTER, requestAttackChapterAction);
		navigate.put(NavigationModule.GET_REPORT_BY_UUID, getReportAction);

		navigate.put(NavigationModule.GET_INFO_FRIEND, getFriendInfoAction);
		navigate.put(NavigationModule.MODIFY_FRIEND_LIST, modifyFriendListAction);
		navigate.put(NavigationModule.MODIFY_BLACK_LIST, modifyBlackListAction);

		navigate.put(NavigationModule.RENAME, setRoleNameAction);
		navigate.put(NavigationModule.FACEID, setRoleFaceIdAction);
		navigate.put(NavigationModule.SET_LEAD_POINT, setRoleLeadAction);

		navigate.put(NavigationModule.GET_SIGN_INFO, getSignInfoAciton);
		navigate.put(NavigationModule.SIGN_TODAY, signTodayAction);
		navigate.put(NavigationModule.SIGN_RETROACTIVE, signRetroactiveAction);
		navigate.put(NavigationModule.GET_SIGN_AWARD, getSignAwardAction);
		navigate.put(NavigationModule.GET_SIGN_HERO, getSignHeroAction);
		navigate.put(NavigationModule.FINISH_NEW_BUILD, finishNewBuildAction);
		navigate.put(NavigationModule.FRIEND_TIP, friendTipAction);

		navigate.put(NavigationModule.REFRESH_CHAPTER_TIMES, refreshChapterTimesAction);

		navigate.put(NavigationModule.EAT_ANIMAL, eatAnimalAction);
		navigate.put(NavigationModule.GET_ANIMAL_INFO, getAnimalInfoAction);

		navigate.put(NavigationModule.GET_ARENA_DATA, getArenaDataAction);
		navigate.put(NavigationModule.GET_ARENA_TARGET, getArenaTargetAction);
		navigate.put(NavigationModule.DARE_TARGET, dareTargetAction);
		navigate.put(NavigationModule.SET_FIGHT_HERO, setFightHeroAction);
		navigate.put(NavigationModule.TEST_ARENA, testArenaFightAction);
		navigate.put(NavigationModule.GET_ARENA_FIGHT_LOAD, getFightLoadAction);
		navigate.put(NavigationModule.GET_Ladder, getLadderAction);
		navigate.put(NavigationModule.GET_NowLadder, getNowLadderAction);
		navigate.put(NavigationModule.GET_LOBBY_INFO, getLobbyInfoAction);
		navigate.put(NavigationModule.CREAT_NEW_ROOM, createNewRoomAction);
		navigate.put(NavigationModule.JOIN_ROOM, joinRoomAction);
		navigate.put(NavigationModule.GET_ROOM_INFO, getRoomInfoAction);
		navigate.put(NavigationModule.QUIT_ROOM, quitRoomAction);
		navigate.put(NavigationModule.KICK_ROLE, kickRoleAction);
		navigate.put(NavigationModule.SAVE_ROOMROLE_FORMATION, saveRoomRoleFormationAction);
		navigate.put(NavigationModule.SET_ROLE_STATUS, setRoleStatusAction);
		navigate.put(NavigationModule.START_BATTLE, startBattleAction);
		navigate.put(NavigationModule.SET_LOADING_PROGRESS, setLoadingProgressAction);
		navigate.put(NavigationModule.UPDATE_LOADING_PROGRESS, updateLoadingAction);

		/** -----------------------------2300+------------------------------ */

		navigate.put(NavigationModule.GET_CITY_INFO, getCityInfoAction);
		navigate.put(NavigationModule.GO_TO_CITY, goToCityAction);
		navigate.put(NavigationModule.JOIN_CITY, joinCityAction);
		navigate.put(NavigationModule.WORLD_INFO, worldInfoAction);
		navigate.put(NavigationModule.GET_FIEF_INFO, getFiefInfoAction);
		navigate.put(NavigationModule.GET_CITY_MINE_FARM, getCityMineFarmAction);
		navigate.put(NavigationModule.GET_CITY_MINE_FARM_INFO, getCityMineFarmInfoAction);
		navigate.put(NavigationModule.OCCUPY_CITY_MINE_OR_FARM, occupyCityMineOrFarmAction);
		navigate.put(NavigationModule.GET_CITY_MINE_FARM_CAN, canUseArmyMineAction);
		navigate.put(NavigationModule.CRAZY_CITY_MINE_FARM, crazyCityMineFarmAction);
		navigate.put(NavigationModule.DROP_CITY_MINE_OR_FARM, dropCityMineOrFarmAction);
		navigate.put(NavigationModule.CONQUER, conquerAction);
		navigate.put(NavigationModule.DROP_VASSAL, dropVassalAction);
		navigate.put(NavigationModule.START_WORLD_FORMATION, startWorldFormationAction);
		navigate.put(NavigationModule.UP_GRADE_RANK, upGradeRankAction);
		navigate.put(NavigationModule.PVP_FIGHT, pvpFightAction);
		navigate.put(NavigationModule.GET_RANK_INFO, getRankInfoAction);
		navigate.put(NavigationModule.GET_RANK_AWARD, getRankAwardAction);
		navigate.put(NavigationModule.GET_VASSAL_INFO, GetVassalInfoAction);
		navigate.put(NavigationModule.GET_WORLD_FORMATION, getAllWorldFormationAction);
		navigate.put(NavigationModule.SAVE_WORLD_FORMATION, saveWorldFormationAction);
		navigate.put(NavigationModule.GET_HERO_LOCATION, getHeroLocationAction);
		navigate.put(NavigationModule.GET_FORMATION_INFO, getWroldFormationAction);
		navigate.put(NavigationModule.CHANGE_WORLD_ARMY_FORMATION, changeWorldArmyFormationAction);
		navigate.put(NavigationModule.SHOW_WORLD_CITY_WALL_INFO, cityShowWorldCityWallInfoAction);
		navigate.put(NavigationModule.MARCH_WORLD_ARMY_FROM_HOME, cityMarchWorldArmyAction);
		navigate.put(NavigationModule.SHOW_SPEED_UP_WORLD_MARCH, cityShowSpeedUpWorldMarchAction);
		navigate.put(NavigationModule.SPEED_UP_WORLD_MARCH, citySpeedUpWorldMarchAction);
		navigate.put(NavigationModule.SHOW_STAY_WORLD_ARMY_CITY, cityShowStayWorldArmyCityAction);
		navigate.put(NavigationModule.CHANG_DEFINFO, changeDefInfoAction);
		navigate.put(NavigationModule.CHANG_COREINFO, changeCoreInfoAction);
		navigate.put(NavigationModule.JOIN_CORE, joinCoreAction);
		navigate.put(NavigationModule.LEAVE_CORE, leaveCoreAction);
		navigate.put(NavigationModule.SHOW_WORLD_MARCH_INFO, cityShowWorldMarchInfoAction);
		navigate.put(NavigationModule.SHOW_WORLD_CITY_INFO, cityShowWorldCityInfoAction);
		navigate.put(NavigationModule.GET_CONTRIBUTE_RANK, cityGetContributeRankAction);
		navigate.put(NavigationModule.MARCH_WORLD_ARMY_FROM_CITY, cityMarchWorldArmyFromCityAction);
		navigate.put(NavigationModule.SHOW_WORLD_MARCH_ROLE_INFO, cityShowWorldMarchRoleInfoAction);
		navigate.put(NavigationModule.SHOW_ONE_WORLD_MARCH, cityShowOneWorldMarchAction);
		navigate.put(NavigationModule.RETREATE_WORLD_MARCH, cityRetreateWorldMarchAction);
		navigate.put(NavigationModule.SHOW_STRONGHOLD, cityShowStrongHoldAction);
		navigate.put(NavigationModule.SET_STRONGHOLD, citySetStrongHoldAction);
		navigate.put(NavigationModule.DISIMISS_WORLD_ARMY, cityDismissArmyAction);
		navigate.put(NavigationModule.GET_ALL_CITY_ARMY, getAllArmyCityAction);
		navigate.put(NavigationModule.LEAVE_DEFINFO, cityLeaveDefAction);
		navigate.put(NavigationModule.SPEED_UP_WORLD_ARMY_ALIVE, citySpeedUpWorldArmyAliveAction);
		navigate.put(NavigationModule.SHOW_SPEED_UP_WORLD_ARMY_ALIVE, cityShowSpeedUpWorldArmyAliveAction);
		navigate.put(NavigationModule.GET_MYLORD_INFO, cityGetMyLordInfoAction);	
		navigate.put(NavigationModule.CLEAR_PUM_INFO,cityClearPumHistoryAction);
		navigate.put(NavigationModule.GO_TO_VILLAGE,cityGoToVillageAction);
		navigate.put(NavigationModule.SHOW_LEGION_CITY,cityShowLegionCityAction);
		/** ----------------------------2400+------------------------------ */
		navigate.put(NavigationModule.UPDATE_HERO_SOLDIER_LV, armsUpdateHeroSoldierLvAction);
		navigate.put(NavigationModule.UPDATE_HERO_SOLDIER_SKILL, armsUpdateHeroSoldierSkillAction);
		navigate.put(NavigationModule.UPDATE_HERO_SOLDIER_QUALITY, armsUpdateHeroSoldierQualityAction);
		navigate.put(NavigationModule.CHANGE_HERO_SOLDIER, armsChangeHeroSoldierAction);

		/** ----------------------------2500+------------------------------ */
		navigate.put(NavigationModule.GET_BABEL_INFO, getBabelInfoAction);
		navigate.put(NavigationModule.FIGHT_BABEL, fightBabelAction);
		navigate.put(NavigationModule.CHOICE_USERID, choiceBabelNpcAction);
		navigate.put(NavigationModule.CHANG_TROOP_DATA, changeTroopDataAction);
		navigate.put(NavigationModule.RESET_TOWER, resetTowerAction);
		navigate.put(NavigationModule.REVIVE_HERO, reviveHeroAction);
		navigate.put(NavigationModule.GET_HERO_INFO, getHeroInfoAction);
		
		/** ----------------------------2600+--------------------------------*/
		navigate.put(NavigationModule.GET_TARGET_INFO, getTargetInfoAction);
		navigate.put(NavigationModule.GET_AWARD_TARGET, getAwardTargetAction);
		navigate.put(NavigationModule.GET_ACTIVE_AWARD, getActiveAwardAction);
		
		
		/** ----------------------------2700+--------------------------------*/
		navigate.put(NavigationModule.SHOW_ACTIVITY, activityShowActivityAction);
		
		/** -----------------------------测试------------------------------- */
		navigate.put(NavigationModule.FIGHT_TEST, testFightAction);
	}

	// 根据消息头获取导航
	public static ActionSupport getAction(short type) {
		return navigate.get(type);
	}

	public static void setLoginAction(LoginAction loginAction) {
		Navigation.loginAction = loginAction;
	}

	public static void setCreatRoleAction(CreatRoleAction creatRoleAction) {
		Navigation.creatRoleAction = creatRoleAction;
	}

	public static void setGetRoleDataAction(GetRoleDataAction getRoleDataAction) {
		Navigation.getRoleDataAction = getRoleDataAction;
	}

	public static void setChatAction(ChatAction chatAction) {
		Navigation.chatAction = chatAction;
	}

	public static void setLevyAction(LevyAction levyAction) {
		Navigation.levyAction = levyAction;
	}

	public static void setGetNeedGoldAction(GetNeedGoldAction getNeedGoldAction) {
		Navigation.getNeedGoldAction = getNeedGoldAction;
	}

	public static void setUpBuildAction(UpBuildAction upBuildAction) {
		Navigation.upBuildAction = upBuildAction;
	}

	public static void setGetIncomeDataAction(GetIncomeDataAction getIncomeDataAction) {
		Navigation.getIncomeDataAction = getIncomeDataAction;
	}

	public static void setOpenBuildAction(OpenBuildAction openBuildAction) {
		Navigation.openBuildAction = openBuildAction;
	}

	public static void setGetBuildDataAction(GetBuildDataAction getBuildDataAction) {
		Navigation.getBuildDataAction = getBuildDataAction;
	}

	public static void setGetBuildQueueDataAction(GetBuildQueueDataAction getBuildQueueDataAction) {
		Navigation.getBuildQueueDataAction = getBuildQueueDataAction;
	}

	public static void setClearBuildQueueTimeAction(ClearBuildQueueTimeAction clearBuildQueueTimeAction) {
		Navigation.clearBuildQueueTimeAction = clearBuildQueueTimeAction;
	}

	public static void setOpenNewBuildQueueAction(OpenNewBuildQueueAction openNewBuildQueueAction) {
		Navigation.openNewBuildQueueAction = openNewBuildQueueAction;
	}

	public static void setFillPopulationAction(FillPopulationAction fillPopulationAction) {
		Navigation.fillPopulationAction = fillPopulationAction;
	}

	public static void setGetOnLineRoleDataAction(GetOnLineRoleDataAction getOnLineRoleDataAction) {
		Navigation.getOnLineRoleDataAction = getOnLineRoleDataAction;
	}

	public static void setGmListAction(GMListAction gmListAction) {
		Navigation.gmListAction = gmListAction;
	}

	public static void setGetDoingTaskAction(GetDoingTaskAction getDoingTaskAction) {
		Navigation.getDoingTaskAction = getDoingTaskAction;
	}

	public static void setCompleteTaskAction(CompleteTaskAction completeTaskAction) {
		Navigation.completeTaskAction = completeTaskAction;
	}

	public static void setNationAction(NationAction nationAction) {
		Navigation.nationAction = nationAction;
	}

	public static void setMailAction(MailAction mailAction) {
		Navigation.mailAction = mailAction;
	}

	public static void setCreatLegionAction(CreatLegionAction creatLegionAction) {
		Navigation.creatLegionAction = creatLegionAction;
	}

	public static void setGetLegionDataAction(GetLegionDataAction getLegionDataAction) {
		Navigation.getLegionDataAction = getLegionDataAction;
	}

	public static void setGetLegionListAction(GetLegionListAction getLegionListAction) {
		Navigation.getLegionListAction = getLegionListAction;
	}

	public static void setApplyJoinLegionAction(ApplyJoinLegionAction applyJoinLegionAction) {
		Navigation.applyJoinLegionAction = applyJoinLegionAction;
	}

	public static void setGetApplyListAction(GetApplyListAction getApplyListAction) {
		Navigation.getApplyListAction = getApplyListAction;
	}

	public static void setAgreeJoinLegionAction(AgreeJoinLegionAction agreeJoinLegionAction) {
		Navigation.agreeJoinLegionAction = agreeJoinLegionAction;
	}

	public static void setGetAllMailAction(GetAllMailAction getAllMailAction) {
		Navigation.getAllMailAction = getAllMailAction;
	}

	public static void setTakeItemMailAction(TakeItemMailAction takeItemMailAction) {
		Navigation.takeItemMailAction = takeItemMailAction;
	}

	public static void setDeleteMailAction(DeleteMailAction deleteMailAction) {
		Navigation.deleteMailAction = deleteMailAction;
	}

	public static void setSetMailCheckedAction(SetMailCheckedAction setMailCheckedAction) {
		Navigation.setMailCheckedAction = setMailCheckedAction;
	}

	public static void setDeputyLegionAction(DeputyLegionAction deputyLegionAction) {
		Navigation.deputyLegionAction = deputyLegionAction;
	}

	public static void setGetPropListAction(GetPropListAction getPropListAction) {
		Navigation.getPropListAction = getPropListAction;
	}

	public static void setChangeFaceIdAction(ChangeFaceIdAction changeFaceIdAction) {
		Navigation.changeFaceIdAction = changeFaceIdAction;
	}

	public static void setChangNoticeAction(ChangNoticeAction changNoticeAction) {
		Navigation.changNoticeAction = changNoticeAction;
	}

	public static void setGetAllMemberAction(GetAllMemberAction getAllMemberAction) {
		Navigation.getAllMemberAction = getAllMemberAction;
	}

	public static void setQuiteLegionAction(QuiteLegionAction quiteLegionAction) {
		Navigation.quiteLegionAction = quiteLegionAction;
	}

	public static void setSearchLegionAction(SearchLegionAction searchLegionAction) {
		Navigation.searchLegionAction = searchLegionAction;
	}

	public static void setUpgradeScienceAction(UpgradeScienceAction upgradeScienceAction) {
		Navigation.upgradeScienceAction = upgradeScienceAction;
	}

	public static void setMarketSellAction(MarketSellAction marketSellAction) {
		Navigation.marketSellAction = marketSellAction;
	}

	public static void setMarketBuyAction(MarketBuyAction marketBuyAction) {
		Navigation.marketBuyAction = marketBuyAction;
	}

	public static void setUsePropAction(UsePropAction usePropAction) {
		Navigation.usePropAction = usePropAction;
	}

	public static void setEquipPropAction(EquipPropAction equipPropAction) {
		Navigation.equipPropAction = equipPropAction;
	}

	public static void setMarketFreshAction(MarketFreshAction marketFreshAction) {
		Navigation.marketFreshAction = marketFreshAction;
	}

	public static void setUninstallEquipAction(UninstallEquipAction uninstallEquipAction) {
		Navigation.uninstallEquipAction = uninstallEquipAction;
	}

	public static void setGetHeroListAction(GetHeroListAction getHeroListAction) {
		Navigation.getHeroListAction = getHeroListAction;
	}

	public static void setQuickDressAction(QuickDressAction quickDressAction) {
		Navigation.quickDressAction = quickDressAction;
	}

	public static void setSingleDressAction(SingleDressAction singleDressAction) {
		Navigation.singleDressAction = singleDressAction;
	}

	public static void setQuickTrainHeroAction(QuickTrainHeroAction quickTrainHeroAction) {
		Navigation.quickTrainHeroAction = quickTrainHeroAction;
	}

	public static void setSaveFormationAction(SaveFormationAction saveFormationAction) {
		Navigation.saveFormationAction = saveFormationAction;
	}

	public static void setPubTalkAction(PubTalkAction pubTalkAction) {
		Navigation.pubTalkAction = pubTalkAction;
	}

	public static void setPubVisitAction(PubVisitAction pubVisitAction) {
		Navigation.pubVisitAction = pubVisitAction;
	}

	public static void setPubHeroDequeueAction(PubHeroDequeueAction pubHeroDequeueAction) {
		Navigation.pubHeroDequeueAction = pubHeroDequeueAction;
	}

	public static void setPubGetInfoAction(PubGetInfoAction pubGetInfoAction) {
		Navigation.pubGetInfoAction = pubGetInfoAction;
	}

	public static void setPubGetMileHerosAction(PubGetMileHerosAction pubGetMileHerosAction) {
		Navigation.pubGetMileHerosAction = pubGetMileHerosAction;
	}

	public static void setGetFormationDataAction(GetFormationDataAction getFormationDataAction) {
		Navigation.getFormationDataAction = getFormationDataAction;
	}

	public static void setGetFormationDataAllAction(GetFormationDataAllAction getFormationDataAllAction) {
		Navigation.getFormationDataAllAction = getFormationDataAllAction;
	}

	public static void setUpdateUseFormationIDAction(UpdateUseFormationIDAction updateUseFormationIDAction) {
		Navigation.updateUseFormationIDAction = updateUseFormationIDAction;
	}

	public static void setUpHeroRebirthAction(UpHeroRebirthAction upHeroRebirthAction) {
		Navigation.upHeroRebirthAction = upHeroRebirthAction;
	}

	public static void setSetAppointScienceAction(SetAppointScienceAction setAppointScienceAction) {
		Navigation.setAppointScienceAction = setAppointScienceAction;
	}

	public static void setStrengthenEquipAction(StrengthenEquipAction strengthenEquipAction) {
		Navigation.strengthenEquipAction = strengthenEquipAction;
	}

	public static void setBuildBarrackAction(BuildBarrackAction buildBarrackAction) {
		Navigation.buildBarrackAction = buildBarrackAction;
	}

	public static void setUpBarrackAction(UpBarrackAction upBarrackAction) {
		Navigation.upBarrackAction = upBarrackAction;
	}

	public static void setSellBarrackAction(SellBarrackAction sellBarrackAction) {
		Navigation.sellBarrackAction = sellBarrackAction;
	}

	public static void setChangeBarrackAction(ChangeBarrackAction changeBarrackAction) {
		Navigation.changeBarrackAction = changeBarrackAction;
	}

	public static void setGetRoleScienceInfoAction(GetRoleScienceInfoAction getRoleScienceInfoAction) {
		Navigation.getRoleScienceInfoAction = getRoleScienceInfoAction;
	}

	public static void setUpgradRoleScienceAction(UpgradRoleScienceAction upgradRoleScienceAction) {
		Navigation.upgradRoleScienceAction = upgradRoleScienceAction;
	}

	public static void setGetRoleScienceQueueAction(GetRoleScienceQueueAction getRoleScienceQueueAction) {
		Navigation.getRoleScienceQueueAction = getRoleScienceQueueAction;
	}

	public static void setClearRoleScienceQueueTimeAction(
			ClearRoleScienceQueueTimeAction clearRoleScienceQueueTimeAction) {
		Navigation.clearRoleScienceQueueTimeAction = clearRoleScienceQueueTimeAction;
	}

	public static void setMarketGetInfo(MarketGetInfo marketGetInfo) {
		Navigation.marketGetInfo = marketGetInfo;
	}

	public static void setRecruitArmsAction(RecruitArmsAction recruitArmsAction) {
		Navigation.recruitArmsAction = recruitArmsAction;
	}

	public static void setDeployArmsAction(DeployArmsAction deployArmsAction) {
		Navigation.deployArmsAction = deployArmsAction;
	}

	public static void setAddArmsNumAction(AddArmsNumAction addArmsNumAction) {
		Navigation.addArmsNumAction = addArmsNumAction;
	}

	public static void setHeartAction(HeartAction heartAction) {
		Navigation.heartAction = heartAction;
	}

	public static void setPubMultipleTalkAction(PubMultipleTalkAction pubMultipleTalkAction) {
		Navigation.pubMultipleTalkAction = pubMultipleTalkAction;
	}

	public static void setGetBarrackDataAction(GetBarrackDataAction getBarrackDataAction) {
		Navigation.getBarrackDataAction = getBarrackDataAction;
	}

	public static void setGetTroopsDataAction(GetTroopsDataAction getTroopsDataAction) {
		Navigation.getTroopsDataAction = getTroopsDataAction;
	}

	public static void setAutoAgreeJoinAction(AutoAgreeJoinAction autoAgreeJoinAction) {
		Navigation.autoAgreeJoinAction = autoAgreeJoinAction;
	}

	public static void setExchangeLegionAction(ExchangeLegionAction exchangeLegionAction) {
		Navigation.exchangeLegionAction = exchangeLegionAction;
	}

	public static void setRejectJoinAction(RejectJoinAction rejectJoinAction) {
		Navigation.rejectJoinAction = rejectJoinAction;
	}

	public static void setRechargeAction(RechargeAction rechargeAction) {
		Navigation.rechargeAction = rechargeAction;
	}

	public static void setGetChapterDataAction(GetChapterDataAction getChapterDataAction) {
		Navigation.getChapterDataAction = getChapterDataAction;
	}

	public static void setRequestAttackChapterAction(RequestAttackChapterAction requestAttackChapterAction) {
		Navigation.requestAttackChapterAction = requestAttackChapterAction;
	}

	public static void setGetAllChapterDataAction(GetAllChapterDataAction getAllChapterDataAction) {
		Navigation.getAllChapterDataAction = getAllChapterDataAction;
	}

	public static void setGetFriendInfoAction(GetFriendInfoAction getFriendInfoAction) {
		Navigation.getFriendInfoAction = getFriendInfoAction;
	}

	public static void setModifyFriendListAction(ModifyFriendListAction modifyFriendListAction) {
		Navigation.modifyFriendListAction = modifyFriendListAction;
	}

	public static void setModifyBlackListAction(ModifyBlackListAction modifyBlackListAction) {
		Navigation.modifyBlackListAction = modifyBlackListAction;
	}

	public static void setAddMaxBagNumAction(AddMaxBagNumAction addMaxBagNumAction) {
		Navigation.addMaxBagNumAction = addMaxBagNumAction;
	}

	public static void setSetRoleNameAction(SetRoleNameAction setRoleNameAction) {
		Navigation.setRoleNameAction = setRoleNameAction;
	}

	public static void setGetSignInfoAciton(GetSignInfoAciton getSignInfoAciton) {
		Navigation.getSignInfoAciton = getSignInfoAciton;
	}

	public static void setSignTodayAction(SignTodayAction signTodayAction) {
		Navigation.signTodayAction = signTodayAction;
	}

	public static void setSignRetroactiveAction(SignRetroactiveAction signRetroactiveAction) {
		Navigation.signRetroactiveAction = signRetroactiveAction;
	}

	public static void setGetSignAwardAction(GetSignAwardAction getSignAwardAction) {
		Navigation.getSignAwardAction = getSignAwardAction;
	}

	public static void setRaidsChapterAction(RaidsChapterAction raidsChapterAction) {
		Navigation.raidsChapterAction = raidsChapterAction;
	}

	public static void setRefreshChapterTimesAction(RefreshChapterTimesAction refreshChapterTimesAction) {
		Navigation.refreshChapterTimesAction = refreshChapterTimesAction;
	}

	public static void setEatAnimalAction(EatAnimalAction eatAnimalAction) {
		Navigation.eatAnimalAction = eatAnimalAction;
	}

	public static void setGetAnimalInfoAction(GetAnimalInfoAction getAnimalInfoAction) {
		Navigation.getAnimalInfoAction = getAnimalInfoAction;
	}

	public static void setGetArenaDataAction(GetArenaDataAction getArenaDataAction) {
		Navigation.getArenaDataAction = getArenaDataAction;
	}

	public static void setGetArenaTargetAction(GetArenaTargetAction getArenaTargetAction) {
		Navigation.getArenaTargetAction = getArenaTargetAction;
	}

	public static void setDareTargetAction(DareTargetAction dareTargetAction) {
		Navigation.dareTargetAction = dareTargetAction;
	}

	public static void setSetFightHeroAction(SetFightHeroAction setFightHeroAction) {
		Navigation.setFightHeroAction = setFightHeroAction;
	}

	public static void setTestArenaFightAction(TestArenaFightAction testArenaFightAction) {
		Navigation.testArenaFightAction = testArenaFightAction;
	}

	public static void setSetRoleFaceIdAction(SetRoleFaceIdAction setRoleFaceIdAction) {
		Navigation.setRoleFaceIdAction = setRoleFaceIdAction;
	}

	public static void setGetDailyTaskAwardAction(GetDailyTaskAwardAction getDailyTaskAwardAction) {
		Navigation.getDailyTaskAwardAction = getDailyTaskAwardAction;
	}

	public static void setQuickCompleteDailyTaskAction(QuickCompleteDailyTaskAction quickCompleteDailyTaskAction) {
		Navigation.quickCompleteDailyTaskAction = quickCompleteDailyTaskAction;
	}

	public static void setGetDailyTaskAction(GetDailyTaskAction getDailyTaskAction) {
		Navigation.getDailyTaskAction = getDailyTaskAction;
	}

	public static void setGetFightLoadAction(GetFightLoadAction getFightLoadAction) {
		Navigation.getFightLoadAction = getFightLoadAction;
	}

	public static void setGetLobbyInfoAction(GetLobbyInfoAction getLobbyInfoAction) {
		Navigation.getLobbyInfoAction = getLobbyInfoAction;
	}

	public static void setCreateNewRoomAction(CreateNewRoomAction createNewRoomAction) {
		Navigation.createNewRoomAction = createNewRoomAction;
	}

	public static void setJoinRoomAction(JoinRoomAction joinRoomAction) {
		Navigation.joinRoomAction = joinRoomAction;
	}

	public static void setGetRoomInfoAction(GetRoomInfoAction getRoomInfoAction) {
		Navigation.getRoomInfoAction = getRoomInfoAction;
	}

	public static void setQuitRoomAction(QuitRoomAction quitRoomAction) {
		Navigation.quitRoomAction = quitRoomAction;
	}

	public static void setKickRoleAction(KickRoleAction kickRoleAction) {
		Navigation.kickRoleAction = kickRoleAction;
	}

	public static void setSaveRoomRoleFormationAction(SaveRoomRoleFormationAction saveRoomRoleFormationAction) {
		Navigation.saveRoomRoleFormationAction = saveRoomRoleFormationAction;
	}

	public static void setSetRoleStatusAction(SetRoleStatusAction setRoleStatusAction) {
		Navigation.setRoleStatusAction = setRoleStatusAction;
	}

	public static void setStartBattleAction(StartBattleAction startBattleAction) {
		Navigation.startBattleAction = startBattleAction;
	}

	public static void setSetLoadingProgressAction(SetLoadingProgressAction setLoadingProgress) {
		Navigation.setLoadingProgressAction = setLoadingProgress;
	}

	public static void setUpdateLoadingAction(UpdateLoadingAction updateLoadingAction) {
		Navigation.updateLoadingAction = updateLoadingAction;
	}

	// public static void setChangeHeroAction(ChangeHeroAction changeHeroAction)
	// {
	// Navigation.changeHeroAction = changeHeroAction;
	// }
	public static void setGetLadderAction(GetLadderAction getLadderAction) {
		Navigation.getLadderAction = getLadderAction;
	}

	public static void setGetNowLadderAction(GetNowLadderAction getNowLadderAction) {
		Navigation.getNowLadderAction = getNowLadderAction;
	}

	public static void setBuyArmyTokenAction(BuyArmyTokenAction buyArmyTokenAction) {
		Navigation.buyArmyTokenAction = buyArmyTokenAction;
	}

	public static void setGetChapterStarsAwardAction(GetChapterStarsAwardAction getChapterStarsAwardAction) {
		Navigation.getChapterStarsAwardAction = getChapterStarsAwardAction;
	}

	/**
	 * ------------------------------------------------------------------------
	 * ---
	 */

	public static void setGetCityInfoAction(GetCityInfoAction getCityInfoAction) {
		Navigation.getCityInfoAction = getCityInfoAction;
	}

	public static void setGoToCityAction(GoToCityAction goToCityAction) {
		Navigation.goToCityAction = goToCityAction;
	}

	public static void setJoinCityAction(JoinCityAction joinCityAction) {
		Navigation.joinCityAction = joinCityAction;
	}

	public static void setWorldInfoAction(WorldInfoAction worldInfoAction) {
		Navigation.worldInfoAction = worldInfoAction;
	}

	public static void setGetFiefInfoAction(GetFiefInfoAction getFiefInfoAction) {
		Navigation.getFiefInfoAction = getFiefInfoAction;
	}

	public static void setChatPrivateAction(ChatPrivateAction chatPrivateAction) {
		Navigation.chatPrivateAction = chatPrivateAction;
	}

	public static void setCancelApplyAction(CancelApplyAction cancelApplyAction) {
		Navigation.cancelApplyAction = cancelApplyAction;
	}

	public static void setAddMaxPeopleNumAction(AddMaxPeopleNumAction addMaxPeopleNumAction) {
		Navigation.addMaxPeopleNumAction = addMaxPeopleNumAction;
	}

	public static void setGetAllStarAction(GetAllStarAction getAllStarAction) {
		Navigation.getAllStarAction = getAllStarAction;
	}

	public static void setGetChapterAwardDataAction(GetChapterAwardDataAction getChapterAwardDataAction) {
		Navigation.getChapterAwardDataAction = getChapterAwardDataAction;
	}

	public static void setGetPartChapterDataAction(GetPartChapterDataAction getPartChapterDataAction) {
		Navigation.getPartChapterDataAction = getPartChapterDataAction;
	}

	public static void setChatLegionAction(ChatLegionAction chatLegionAction) {
		Navigation.chatLegionAction = chatLegionAction;
	}

	public static void setGetSignHeroAction(GetSignHeroAction getSignHeroAction) {
		Navigation.getSignHeroAction = getSignHeroAction;
	}

	public static void setGetCityMineFarmAction(GetCityMineFarmAction getCityMineFarmAction) {
		Navigation.getCityMineFarmAction = getCityMineFarmAction;
	}

	public static void setGetCityMineFarmInfoAction(GetCityMineFarmInfoAction getCityMineFarmInfoAction) {
		Navigation.getCityMineFarmInfoAction = getCityMineFarmInfoAction;
	}

	public static void setOccupyCityMineOrFarmAction(OccupyCityMineOrFarmAction occupyCityMineOrFarmAction) {
		Navigation.occupyCityMineOrFarmAction = occupyCityMineOrFarmAction;
	}

	public static void setCrazyCityMineFarmAction(CrazyCityMineFarmAction crazyCityMineFarmAction) {
		Navigation.crazyCityMineFarmAction = crazyCityMineFarmAction;
	}

	public static void setDropCityMineOrFarmAction(DropCityMineOrFarmAction dropCityMineOrFarmAction) {
		Navigation.dropCityMineOrFarmAction = dropCityMineOrFarmAction;
	}

	public static void setGetInfoExchangeAction(GetInfoExchangeAction getInfoExchangeAction) {
		Navigation.getInfoExchangeAction = getInfoExchangeAction;
	}

	public static void setGetGoldMarketInfoAction(GetGoldMarketInfoAction getGoldMarketInfoAction) {
		Navigation.getGoldMarketInfoAction = getGoldMarketInfoAction;
	}

	public static void setBuyGoldMarketAction(BuyGoldMarketAction buyGoldMarketAction) {
		Navigation.buyGoldMarketAction = buyGoldMarketAction;
	}

	public static void setGetVipInfoAction(GetVipInfoAction getVipInfoAction) {
		Navigation.getVipInfoAction = getVipInfoAction;
	}

	public static void setGetVipRawardInfoAction(GetVipRawardInfoAction getVipRawardInfoAction) {
		Navigation.getVipRawardInfoAction = getVipRawardInfoAction;
	}

	public static void setConquerAction(ConquerAction conquerAction) {
		Navigation.conquerAction = conquerAction;
	}

	public static void setGetVipRawardAction(GetVipRawardAction getVipRawardAction) {
		Navigation.getVipRawardAction = getVipRawardAction;
	}

	public static void setUpGradeRankAction(UpGradeRankAction upGradeRankAction) {
		Navigation.upGradeRankAction = upGradeRankAction;
	}

	public static void setPvpFightAction(PvpFightAction pvpFightAction) {
		Navigation.pvpFightAction = pvpFightAction;
	}

	public static void setGetRankInfoAction(GetRankInfoAction getRankInfoAction) {
		Navigation.getRankInfoAction = getRankInfoAction;
	}

	public static void setGetRankAwardAction(GetRankAwardAction getRankAwardAction) {
		Navigation.getRankAwardAction = getRankAwardAction;
	}

	public static void setGetVassalInfoAction(GetVassalInfoAction getVassalInfoAction) {
		GetVassalInfoAction = getVassalInfoAction;
	}

	public static void setDropVassalAction(DropVassalAction dropVassalAction) {
		Navigation.dropVassalAction = dropVassalAction;
	}

	public static void setTestFightAction(TestFightAction testFightAction) {
		Navigation.testFightAction = testFightAction;
	}

	public static void setGetBabelInfoAction(GetBabelInfoAction getBabelInfoAction) {
		Navigation.getBabelInfoAction = getBabelInfoAction;
	}

	public static void setFightBabelAction(FightBabelAction fightBabelAction) {
		Navigation.fightBabelAction = fightBabelAction;
	}

	public static void setPubEnterAction(PubEnterAction pubEnterAction) {
		Navigation.pubEnterAction = pubEnterAction;
	}

	public static void setPubMileConvertHeroAction(PubMileConvertHeroAction pubMileConvertHeroAction) {
		Navigation.pubMileConvertHeroAction = pubMileConvertHeroAction;
	}

	public static void setArmsChangeHeroSoldierAction(ArmsChangeHeroSoldierAction armsChangeHeroSoldierAction) {
		Navigation.armsChangeHeroSoldierAction = armsChangeHeroSoldierAction;
	}

	public static void setArmsUpdateHeroSoldierLvAction(ArmsUpdateHeroSoldierLvAction armsUpdateHeroSoldierLvAction) {
		Navigation.armsUpdateHeroSoldierLvAction = armsUpdateHeroSoldierLvAction;
	}

	public static void setArmsUpdateHeroSoldierQualityAction(
			ArmsUpdateHeroSoldierQualityAction armsUpdateHeroSoldierQualityAction) {
		Navigation.armsUpdateHeroSoldierQualityAction = armsUpdateHeroSoldierQualityAction;
	}

	public static void setArmsUpdateHeroSoldierSkillAction(
			ArmsUpdateHeroSoldierSkillAction armsUpdateHeroSoldierSkillAction) {
		Navigation.armsUpdateHeroSoldierSkillAction = armsUpdateHeroSoldierSkillAction;
	}

	public static void setRecoveryEquipAction(RecoveryEquipAction recoveryEquipAction) {
		Navigation.recoveryEquipAction = recoveryEquipAction;
	}

	public static void setRefineEquipAction(RefineEquipAction refineEquipAction) {
		Navigation.refineEquipAction = refineEquipAction;
	}

	public static void setChangePrefixAction(ChangePrefixAction changePrefixAction) {
		Navigation.changePrefixAction = changePrefixAction;
	}

	public static void setGetHeroArmyInfoAction(GetHeroArmyInfoAction getHeroArmyInfoAction) {
		Navigation.getHeroArmyInfoAction = getHeroArmyInfoAction;
	}

	public static void setGetRebLvInfoAction(GetRebLvInfoAction getRebLvInfoAction) {
		Navigation.getRebLvInfoAction = getRebLvInfoAction;
	}

	public static void setHeroUpdateSkillLvAction(HeroUpdateSkillLvAction heroUpdateSkillLvAction) {
		Navigation.heroUpdateSkillLvAction = heroUpdateSkillLvAction;
	}

	public static void setRoleLvUpAction(RoleLvUpAction roleLvUpAction) {
		Navigation.roleLvUpAction = roleLvUpAction;
	}

	public static void setRoleGetLvDataAction(RoleGetLvDataAction roleGetLvDataAction) {
		Navigation.roleGetLvDataAction = roleGetLvDataAction;
	}

	public static void setGetAllHeroArmsInfoAction(GetAllHeroArmsInfoAction getAllHeroArmsInfoAction) {
		Navigation.getAllHeroArmsInfoAction = getAllHeroArmsInfoAction;
	}

	public static void setAttachHeroAction(AttachHeroAction attachHeroAction) {
		Navigation.attachHeroAction = attachHeroAction;
	}

	public static void setCancelAttachAction(CancelAttachAction cancelAttachAction) {
		Navigation.cancelAttachAction = cancelAttachAction;
	}

	public static void setGetInfoAttach(GetInfoAttach getInfoAttach) {
		Navigation.getInfoAttach = getInfoAttach;
	}

	public static void setGetSimpleBuildDataAction(GetSimpleBuildDataAction getSimpleBuildDataAction) {
		Navigation.getSimpleBuildDataAction = getSimpleBuildDataAction;
	}

	public static void setBuildTypeLvUpAction(BuildTypeLvUpAction buildTypeLvUpAction) {
		Navigation.buildTypeLvUpAction = buildTypeLvUpAction;
	}

	public static void setGetNumResourceAction(GetNumResourceAction getNumResourceAction) {
		Navigation.getNumResourceAction = getNumResourceAction;
	}

	public static void setGetVisitAwardAction(GetVisitAwardAction getVisitAwardAction) {
		Navigation.getVisitAwardAction = getVisitAwardAction;
	}

	public static void setPubShowWineDeskAction(PubShowWineDeskAction pubShowWineDeskAction) {
		Navigation.pubShowWineDeskAction = pubShowWineDeskAction;
	}

	public static void setGetMapInfoAction(GetMapInfoAction getMapInfoAction) {
		Navigation.getMapInfoAction = getMapInfoAction;
	}

	public static void setPubChangeDeskHeroAction(PubChangeDeskHeroAction pubChangeDeskHeroAction) {
		Navigation.pubChangeDeskHeroAction = pubChangeDeskHeroAction;
	}

	public static void setPubShowMakeWineAction(PubShowMakeWineAction pubShowMakeWineAction) {
		Navigation.pubShowMakeWineAction = pubShowMakeWineAction;
	}

	public static void setPubDrinkWineAction(PubDrinkWineAction pubDrinkWineAction) {
		Navigation.pubDrinkWineAction = pubDrinkWineAction;
	}

	public static void setPubMakeWineAction(PubMakeWineAction pubMakeWineAction) {
		Navigation.pubMakeWineAction = pubMakeWineAction;
	}

	public static void setPubGetWineAction(PubGetWineAction pubGetWineAction) {
		Navigation.pubGetWineAction = pubGetWineAction;
	}

	public static void setPubUnlockWineDeskAction(PubUnlockWineDeskAction pubUnlockWineDeskAction) {
		Navigation.pubUnlockWineDeskAction = pubUnlockWineDeskAction;
	}

	public static void setPubSpeedUpAction(PubSpeedUpAction pubSpeedUpAction) {
		Navigation.pubSpeedUpAction = pubSpeedUpAction;
	}

	public static void setGetFightInfoAction(GetFightInfoAction getFightInfoAction) {
		Navigation.getFightInfoAction = getFightInfoAction;
	}

	public static void setSetRoleLeadAction(SetRoleLeadAction setRoleLeadAction) {
		Navigation.setRoleLeadAction = setRoleLeadAction;
	}

	public static void setPropMakeIronAction(PropMakeIronAction propMakeIronAction) {
		Navigation.propMakeIronAction = propMakeIronAction;
	}

	public static void setPropShowMakeIronAction(PropShowMakeIronAction propShowMakeIronAction) {
		Navigation.propShowMakeIronAction = propShowMakeIronAction;
	}

	public static void setPropGetIronAction(PropGetIronAction propGetIronAction) {
		Navigation.propGetIronAction = propGetIronAction;
	}

	public static void setPropGetVisitTreasureAction(PropGetVisitTreasureAction propGetVisitTreasureAction) {
		Navigation.propGetVisitTreasureAction = propGetVisitTreasureAction;
	}

	public static void setPropVisitTreasureAction(PropVisitTreasureAction propVisitTreasureAction) {
		Navigation.propVisitTreasureAction = propVisitTreasureAction;
	}

	public static void setPropShowStrengthenAction(PropShowStrengthenAction propShowStrengthenAction) {
		Navigation.propShowStrengthenAction = propShowStrengthenAction;
	}

	public static void setScienceOfferScienceInfoAction(ScienceOfferScienceInfoAction scienceOfferScienceInfoAction) {
		Navigation.scienceOfferScienceInfoAction = scienceOfferScienceInfoAction;
	}

	public static void setScienceGetOfferScienceAwardAction(
			ScienceGetOfferScienceAwardAction scienceGetOfferScienceAwardAction) {
		Navigation.scienceGetOfferScienceAwardAction = scienceGetOfferScienceAwardAction;
	}

	public static void setStopAppAction(StopAppAction stopAppAction) {
		Navigation.stopAppAction = stopAppAction;
	}

	public static void setHeroRetrainArmyAction(HeroRetrainArmyAction heroRetrainArmyAction) {
		Navigation.heroRetrainArmyAction = heroRetrainArmyAction;
	}

	public static void setHeroUpDutyLvAction(HeroUpDutyLvAction heroUpDutyLvAction) {
		Navigation.heroUpDutyLvAction = heroUpDutyLvAction;
	}

	public static void setHeroUpRankLvAction(HeroUpRankLvAction heroUpRankLvAction) {
		Navigation.heroUpRankLvAction = heroUpRankLvAction;
	}

	public static void setHeroInsteadArmyAction(HeroInsteadArmyAction heroInsteadArmyAction) {
		Navigation.heroInsteadArmyAction = heroInsteadArmyAction;
	}

	public static void setHeroShowDutyAction(HeroShowDutyAction heroShowDutyAction) {
		Navigation.heroShowDutyAction = heroShowDutyAction;
	}

	public static void setHeroShowRankAction(HeroShowRankAction heroShowRankAction) {
		Navigation.heroShowRankAction = heroShowRankAction;
	}

	public static void setHeroShowHeroDetailAction(HeroShowHeroDetailAction heroShowHeroDetailAction) {
		Navigation.heroShowHeroDetailAction = heroShowHeroDetailAction;
	}

	public static void setFinishNewBuildAction(FinishNewBuildAction finishNewBuildAction) {
		Navigation.finishNewBuildAction = finishNewBuildAction;
	}

	public static void setPropShowEquipAction(PropShowEquipAction propShowEquipAction) {
		Navigation.propShowEquipAction = propShowEquipAction;
	}

	public static void setHeroShowRetrainAction(HeroShowRetrainAction heroShowRetrainAction) {
		Navigation.heroShowRetrainAction = heroShowRetrainAction;
	}

	public static void setInComeShowSpeedUpLevyAction(InComeShowSpeedUpLevyAction inComeShowSpeedUpLevyAction) {
		Navigation.inComeShowSpeedUpLevyAction = inComeShowSpeedUpLevyAction;
	}

	public static void setInComeSpeedUpLevyAction(InComeSpeedUpLevyAction inComeSpeedUpLevyAction) {
		Navigation.inComeSpeedUpLevyAction = inComeSpeedUpLevyAction;
	}

	public static void setHeroCompoundHeroAction(HeroCompoundHeroAction heroCompoundHeroAction) {
		Navigation.heroCompoundHeroAction = heroCompoundHeroAction;
	}

	public static void setFriendTipAction(FriendTipAction friendTipAction) {
		Navigation.friendTipAction = friendTipAction;
	}

	/**
	 * @param barrackShowOffensiveAction the barrackShowOffensiveAction to set
	 */
	public static void setBarrackShowMakeExploitAction(BarrackShowMakeExploitAction barrackShowMakeExploitAction) {
		Navigation.barrackShowMakeExploitAction = barrackShowMakeExploitAction;
	}

	/**
	 * @param barrackGetExploitAction the barrackGetExploitAction to set
	 */
	public static void setBarrackGetExploitAction(BarrackGetExploitAction barrackGetExploitAction) {
		Navigation.barrackGetExploitAction = barrackGetExploitAction;
	}

	/**
	 * @param barrackUpArmyuSkillAction the barrackUpArmyuSkillAction to set
	 */
	public static void setBarrackUpArmySkillAction(BarrackUpArmySkillAction barrackUpArmySkillAction) {
		Navigation.barrackUpArmySkillAction = barrackUpArmySkillAction;
	}

	/**
	 * @param barrackVisitOffensiveAction the barrackVisitOffensiveAction to set
	 */
	public static void setBarrackVisitOffensiveAction(BarrackVisitOffensiveAction barrackVisitOffensiveAction) {
		Navigation.barrackVisitOffensiveAction = barrackVisitOffensiveAction;
	}

	/**
	 * @param barrackShowArmySkillAction the barrackShowArmySkillAction to set
	 */
	public static void setBarrackShowArmySkillAction(BarrackShowArmySkillAction barrackShowArmySkillAction) {
		Navigation.barrackShowArmySkillAction = barrackShowArmySkillAction;
	}

	public static void setShowAuctionInfoAction(ShowAuctionInfoAction showAuctionInfoAction) {
		Navigation.showAuctionInfoAction = showAuctionInfoAction;
	}

	public static void setSellAuctionAction(SellAuctionAction sellAuctionAction) {
		Navigation.sellAuctionAction = sellAuctionAction;
	}

	public static void setBuyAuctionAction(BuyAuctionAction buyAuctionAction) {
		Navigation.buyAuctionAction = buyAuctionAction;
	}

	public static void setCancelAuctionAction(CancelAuctionAction cancelAuctionAction) {
		Navigation.cancelAuctionAction = cancelAuctionAction;
	}

	public static void setBarrackShowVisitOffensiveAction(
			BarrackShowVisitOffensiveAction barrackShowVisitOffensiveAction) {
		Navigation.barrackShowVisitOffensiveAction = barrackShowVisitOffensiveAction;
	}

	public static void setMarketVisitSaleAction(MarketVisitSaleAction marketVisitSaleAction) {
		Navigation.marketVisitSaleAction = marketVisitSaleAction;
	}

	public static void setMarketBargainItemsAction(MarketBargainItemsAction marketBargainItemsAction) {
		Navigation.marketBargainItemsAction = marketBargainItemsAction;
	}

	public static void setMarketShowBargainItemsAction(MarketShowBargainItemsAction marketShowBargainItemsAction) {
		Navigation.marketShowBargainItemsAction = marketShowBargainItemsAction;
	}

	public static void setShowAuctionSelfAction(ShowAuctionSelfAction showAuctionSelfAction) {
		Navigation.showAuctionSelfAction = showAuctionSelfAction;
	}

	public static void setHeroShowBuyManualAction(HeroShowBuyManualAction heroShowBuyManualAction) {
		Navigation.heroShowBuyManualAction = heroShowBuyManualAction;
	}

	public static void setHeroBuyManualAction(HeroBuyManualAction heroBuyManualAction) {
		Navigation.heroBuyManualAction = heroBuyManualAction;
	}

	public static void setGetAllWorldFormationAction(GetAllWorldFormationAction getAllWorldFormationAction) {
		Navigation.getAllWorldFormationAction = getAllWorldFormationAction;
	}

	public static void setSaveWorldFormationAction(SaveWorldFormationAction saveWorldFormationAction) {
		Navigation.saveWorldFormationAction = saveWorldFormationAction;
	}

	public static void setGetHeroLocationAction(GetHeroLocationAction getHeroLocationAction) {
		Navigation.getHeroLocationAction = getHeroLocationAction;
	}

	public static void setCityShowWorldCityWallInfoAction(
			CityShowWorldCityWallInfoAction cityShowWorldCityWallInfoAction) {
		Navigation.cityShowWorldCityWallInfoAction = cityShowWorldCityWallInfoAction;
	}

	public static void setBarrackGetOffensiveRankAction(BarrackGetOffensiveRankAction barrackGetOffensiveRankAction) {
		Navigation.barrackGetOffensiveRankAction = barrackGetOffensiveRankAction;
	}

	public static void setCityMarchWorldArmyAction(CityMarchWorldArmyAction cityMarchWorldArmyAction) {
		Navigation.cityMarchWorldArmyAction = cityMarchWorldArmyAction;
	}

	public static void setCityShowSpeedUpWorldMarchAction(
			CityShowSpeedUpWorldMarchAction cityShowSpeedUpWorldMarchAction) {
		Navigation.cityShowSpeedUpWorldMarchAction = cityShowSpeedUpWorldMarchAction;
	}

	public static void setCitySpeedUpWorldMarchAction(CitySpeedUpWorldMarchAction citySpeedUpWorldMarchAction) {
		Navigation.citySpeedUpWorldMarchAction = citySpeedUpWorldMarchAction;
	}

	public static void setCityShowStayWorldArmyCityAction(
			CityShowStayWorldArmyCityAction cityShowStayWorldArmyCityAction) {
		Navigation.cityShowStayWorldArmyCityAction = cityShowStayWorldArmyCityAction;
	}

	public static void setChangeDefInfoAction(ChangeDefInfoAction changeDefInfoAction) {
		Navigation.changeDefInfoAction = changeDefInfoAction;
	}

	public static void setChangeCoreInfoAction(ChangeCoreInfoAction changeCoreInfoAction) {
		Navigation.changeCoreInfoAction = changeCoreInfoAction;
	}

	public static void setCityShowWorldMarchInfoAction(CityShowWorldMarchInfoAction cityShowWorldMarchInfoAction) {
		Navigation.cityShowWorldMarchInfoAction = cityShowWorldMarchInfoAction;
	}

	public static void setCityShowWorldCityInfoAction(CityShowWorldCityInfoAction cityShowWorldCityInfoAction) {
		Navigation.cityShowWorldCityInfoAction = cityShowWorldCityInfoAction;
	}

	public static void setCityGetContributeRankAction(CityGetContributeRankAction cityGetContributeRankAction) {
		Navigation.cityGetContributeRankAction = cityGetContributeRankAction;
	}

	public static void setCityMarchWorldArmyFromCityAction(
			CityMarchWorldArmyFromCityAction cityMarchWorldArmyFromCityAction) {
		Navigation.cityMarchWorldArmyFromCityAction = cityMarchWorldArmyFromCityAction;
	}

	public static void setJoinCoreAction(JoinCoreAction joinCoreAction) {
		Navigation.joinCoreAction = joinCoreAction;
	}

	public static void setLeaveCoreAction(LeaveCoreAction leaveCoreAction) {
		Navigation.leaveCoreAction = leaveCoreAction;
	}

	public static void setCityShowWorldMarchRoleInfoAction(
			CityShowWorldMarchRoleInfoAction cityShowWorldMarchRoleInfoAction) {
		Navigation.cityShowWorldMarchRoleInfoAction = cityShowWorldMarchRoleInfoAction;
	}

	public static void setCityShowOneWorldMarchAction(CityShowOneWorldMarchAction cityShowOneWorldMarchAction) {
		Navigation.cityShowOneWorldMarchAction = cityShowOneWorldMarchAction;
	}

	public static void setCityRetreateWorldMarchAction(CityRetreateWorldMarchAction cityRetreateWorldMarchAction) {
		Navigation.cityRetreateWorldMarchAction = cityRetreateWorldMarchAction;
	}

	public static void setCityShowStrongHoldAction(CityShowStrongHoldAction cityShowStrongHoldAction) {
		Navigation.cityShowStrongHoldAction = cityShowStrongHoldAction;
	}

	public static void setCitySetStrongHoldAction(CitySetStrongHoldAction citySetStrongHoldAction) {
		Navigation.citySetStrongHoldAction = citySetStrongHoldAction;
	}

	public static void setCityDismissArmyAction(CityDismissArmyAction cityDismissArmyAction) {
		Navigation.cityDismissArmyAction = cityDismissArmyAction;
	}

	public static void setMarketShowWorldMarketItemsAction(
			MarketShowWorldMarketItemsAction marketShowWorldMarketItemsAction) {
		Navigation.marketShowWorldMarketItemsAction = marketShowWorldMarketItemsAction;
	}

	public static void setMarketBuyWorldMarketItemsAction(
			MarketBuyWorldMarketItemsAction marketBuyWorldMarketItemsAction) {
		Navigation.marketBuyWorldMarketItemsAction = marketBuyWorldMarketItemsAction;
	}

	public static void setCanUseArmyMineAction(CanUseArmyMineAction canUseArmyMineAction) {
		Navigation.canUseArmyMineAction = canUseArmyMineAction;
	}

	public static void setGetAllArmyCityAction(GetAllArmyCityAction getAllArmyCityAction) {
		Navigation.getAllArmyCityAction = getAllArmyCityAction;
	}

	public static void setGetNumnationAction(GetNumnationAction getNumnationAction) {
		Navigation.getNumnationAction = getNumnationAction;
	}

	public static void setCityLeaveDefAction(CityLeaveDefAction cityLeaveDefAction) {
		Navigation.cityLeaveDefAction = cityLeaveDefAction;
	}

	public static void setStartWorldFormationAction(StartWorldFormationAction startWorldFormationAction) {
		Navigation.startWorldFormationAction = startWorldFormationAction;
	}

	public static void setGetWroldFormationAction(GetWroldFormationAction getWroldFormationAction) {
		Navigation.getWroldFormationAction = getWroldFormationAction;
	}

	public static void setChangeWorldArmyFormationAction(ChangeWorldArmyFormationAction changeWorldArmyFormationAction) {
		Navigation.changeWorldArmyFormationAction = changeWorldArmyFormationAction;
	}

	public static void setCitySpeedUpWorldArmyAliveAction(
			CitySpeedUpWorldArmyAliveAction citySpeedUpWorldArmyAliveAction) {
		Navigation.citySpeedUpWorldArmyAliveAction = citySpeedUpWorldArmyAliveAction;
	}

	public static void setCityShowSpeedUpWorldArmyAliveAction(
			CityShowSpeedUpWorldArmyAliveAction cityShowSpeedUpWorldArmyAliveAction) {
		Navigation.cityShowSpeedUpWorldArmyAliveAction = cityShowSpeedUpWorldArmyAliveAction;
	}
	public static void setGetReportAction(GetReportAction getReportAction) {
		Navigation.getReportAction = getReportAction;
	}

	public static void setPropShowAddIronStrengthenAction(PropShowAddIronStrengthenAction propShowAddIronStrengthenAction) {
		Navigation.propShowAddIronStrengthenAction = propShowAddIronStrengthenAction;
	}

	/**
	 * @return the inComeGetPrestigeAction
	 */
	public static InComeGetPrestigeAction getInComeGetPrestigeAction() {
		return inComeGetPrestigeAction;
	}

	/**
	 * @param inComeGetPrestigeAction the inComeGetPrestigeAction to set
	 */
	public static void setInComeGetPrestigeAction(InComeGetPrestigeAction inComeGetPrestigeAction) {
		Navigation.inComeGetPrestigeAction = inComeGetPrestigeAction;
	}

	/**
	 * @return the cityGetMyLordInfoAction
	 */
	public static CityGetMyLordInfoAction getCityGetMyLordInfoAction() {
		return cityGetMyLordInfoAction;
	}

	/**
	 * @param cityGetMyLordInfoAction the cityGetMyLordInfoAction to set
	 */
	public static void setCityGetMyLordInfoAction(CityGetMyLordInfoAction cityGetMyLordInfoAction) {
		Navigation.cityGetMyLordInfoAction = cityGetMyLordInfoAction;
	}

	/**
	 * @return the cityClearPumHistoryAction
	 */
	public static CityClearPumHistoryAction getCityClearPumHistoryAction() {
		return cityClearPumHistoryAction;
	}

	/**
	 * @param cityClearPumHistoryAction the cityClearPumHistoryAction to set
	 */
	public static void setCityClearPumHistoryAction(CityClearPumHistoryAction cityClearPumHistoryAction) {
		Navigation.cityClearPumHistoryAction = cityClearPumHistoryAction;
	}

	public static void setUseKeyAction(UseKeyAction useKeyAction) {
		Navigation.useKeyAction = useKeyAction;
	}

	public static void setGetTargetInfoAction(GetTargetInfoAction getTargetInfoAction) {
		Navigation.getTargetInfoAction = getTargetInfoAction;
	}
	public static void setGetAwardTargetAction(GetAwardTargetAction getAwardTargetAction) {
		Navigation.getAwardTargetAction = getAwardTargetAction;
	}


	public static void setInComeShowRankInfoAction(InComeShowRankInfoAction inComeShowRankInfoAction) {
		Navigation.inComeShowRankInfoAction = inComeShowRankInfoAction;
	}

	public static void setGetActiveAwardAction(GetActiveAwardAction getActiveAwardAction) {
		Navigation.getActiveAwardAction = getActiveAwardAction;
	}
	public static void setSetCityAction(SetCityAction setCityAction) {
		Navigation.setCityAction = setCityAction;
	}
	public static void setChangeShortNameAction(ChangeShortNameAction changeShortNameAction) {
		Navigation.changeShortNameAction = changeShortNameAction;
	}

	public static void setCityGoToVillageAction(CityGoToVillageAction cityGoToVillageAction) {
		Navigation.cityGoToVillageAction = cityGoToVillageAction;
	}
	public static void setGetBuildInfoAction(GetBuildInfoAction getBuildInfoAction) {
		Navigation.getBuildInfoAction = getBuildInfoAction;
	}

	public static void setMarketRefreshPrestigeWorldMarketItemAction(MarketRefreshPrestigeWorldMarketItemAction marketRefreshPrestigeWorldMarketItemAction) {
		Navigation.marketRefreshPrestigeWorldMarketItemAction = marketRefreshPrestigeWorldMarketItemAction;
	}

	public static void setMarketUnlockPrestigeMarketLimitAction(MarketUnlockPrestigeMarketLimitAction marketUnlockPrestigeMarketLimitAction) {
		Navigation.marketUnlockPrestigeMarketLimitAction = marketUnlockPrestigeMarketLimitAction;
	}

	public static void setCityShowLegionCityAction(CityShowLegionCityAction cityShowLegionCityAction) {
		Navigation.cityShowLegionCityAction = cityShowLegionCityAction;
	}

	public static void setChatCountryAction(ChatCountryAction chatCountryAction) {
		Navigation.chatCountryAction = chatCountryAction;
	}
	public static void setChoiceBabelNpcAction(ChoiceBabelNpcAction choiceBabelNpcAction) {
		Navigation.choiceBabelNpcAction = choiceBabelNpcAction;
	}
	public static void setChangeTroopDataAction(ChangeTroopDataAction changeTroopDataAction) {
		Navigation.changeTroopDataAction = changeTroopDataAction;
	}
	public static void setResetTowerAction(ResetTowerAction resetTowerAction) {
		Navigation.resetTowerAction = resetTowerAction;
	}
	public static void setReviveHeroAction(ReviveHeroAction reviveHeroAction) {
		Navigation.reviveHeroAction = reviveHeroAction;
	}

	public static void setInComeShowLevyInfoAction(InComeShowLevyInfoAction inComeShowLevyInfoAction) {
		Navigation.inComeShowLevyInfoAction = inComeShowLevyInfoAction;
	}
	public static void setGetHeroInfoAction(GetHeroInfoAction getHeroInfoAction) {
		Navigation.getHeroInfoAction = getHeroInfoAction;
	}
	
	public static void setActivityShowActivityAction(ActivityShowActivityAction activityShowActivityAction) {
		Navigation.activityShowActivityAction = activityShowActivityAction;
	}

}
