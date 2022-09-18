package com.ubivashka.plasmovoice.audio.sources;

import com.ubivashka.plasmovoice.audio.player.ISoundPlayer;

public abstract class AbstractPlayerAudioSource implements IPlayerAudioSource {
	protected boolean canHearSource = true;
	protected boolean turnOffOnLeave = true;

	@Override
	public boolean canHearSource() {
		return canHearSource;
	}

	@Override
	public void setHearSource(boolean canHearSource) {
		this.canHearSource = canHearSource;
	}

	@Override
	public boolean isTurnOffOnLeave() {
		return turnOffOnLeave;
	}

	@Override
	public void setTurnOffOnLeave(boolean turnOffOnLeave) {
		this.turnOffOnLeave = turnOffOnLeave;
	}
}
