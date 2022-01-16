package com.ubivashka.plasmovoice.audio.sources;

import com.ubivashka.plasmovoice.audio.recorder.ISoundPlayer;

public abstract class AbstractPlayerAudioSource implements IPlayerAudioSource {

	protected final ISoundPlayer soundPlayer;

	public AbstractPlayerAudioSource(ISoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

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
