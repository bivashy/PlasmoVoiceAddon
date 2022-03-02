# PlasmoVoiceAddon

![Java](https://img.shields.io/badge/Java-8%2B-brightgreen)
[![Jitpack](https://jitpack.io/v/U61vashka/PlasmoVoiceAddon.svg)](https://jitpack.io/#U61vashka/PlasmoVoiceAddon) 
![Lines of code](https://img.shields.io/tokei/lines/github/U61vashka/PlasmoVoiceAddon?label=Lines%20of%20code) 

[![Spigot Downloads](https://img.shields.io/spiget/downloads/99253?label=[Spigot]%20Downloads)](https://www.spigotmc.org/resources/plasmovoiceaddon.99253/)
[![Github Releases Downloads](https://img.shields.io/github/downloads/U61vashka/PlasmoVoiceAddon/total?label=[Github]%20Releases%20downloads)](https://github.com/U61vashka/PlasmoVoiceAddon/releases)

![Tested Spigot version](https://img.shields.io/badge/Tested%20on-Spigot%201.16.4-informational)
[![Spigot Rating](https://img.shields.io/spiget/rating/99253?label=[Spigot]%20Rating)](https://www.spigotmc.org/resources/plasmovoiceaddon.99253/) 


![Plugin logo](https://user-images.githubusercontent.com/85439143/150570814-1e3f3e00-7ec7-4972-a888-cfbb32b8ea6f.png)


PlasmoVoiceAddon is a music player (Supports mp3 and wav) from url or local file. And provides API for developers for playing music.

> Important! This is plugin depends on plugin PlasmoVoice. And you cannot play music for the player that not downloaded mod PlasmoVoice

**How to install plugin**:
   1. Download jar from spigot or github releases
   2. Place in your spigot plugins folder
   3. Launch server

**Plugin commands**:
   1. /musicfile [file name in PlasmoVoiceAddon folder (with extension)]
   2. /musicurl [URL to sound]

**Command examples**:
   1. /musicfile piano2.wav - Will be played file piano2.wav file with format wav
   2. /musicfile piano2.mp3 - Will be played file piano2.mp3 file with format mp3
   3. /musicurl https://audio.jukehost.co.uk/L3HgZZAWd0GO38Ghw4xxreITjDk1krXL - Will be played sound from url with format mp3

**Developer API**:
  **How to play sound?**
  1. First we need to get InputStream from our source:
        
    //  From file:
    File file = //Your music file
    InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));


    // From URL:
    URL url = //Your music url
    InputStream inputStream = new BufferedInputStream(url.openStream());
  2. We need to build ISound from our InputStream
    
    ISound sound = new SoundBuilder(inputStream).build();

  3. Play ISound from AudioSource (currently only PlayerAudioSource available)
    
    Player player = //Your player
    int soundDistance = 100; //Maximum distance that will be music played

    PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class)
    PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(),
								addon.getPlasmoVoiceSoundPlayer());
    playerAudioSource.sendAudioData(sound, soundDistance);
      