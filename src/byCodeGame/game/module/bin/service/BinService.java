package byCodeGame.game.module.bin.service;

import byCodeGame.game.entity.bo.Role;
import byCodeGame.game.remote.Message;

public interface BinService {
	Message convert(Role role, byte position);

	Message putBinSpaceProp(Role role, byte position, byte propPosition, int propServiceId);

	Message removeBinSpaceProp(Role role, byte position, byte propPosition);

}
