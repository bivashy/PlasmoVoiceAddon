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
  2. We need to find ISoundFormat from InputStream using PlasmoVoiceAddon main class
    
    PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    Optional<ISoundFormat> soundFormat = addon.getSoundFormatHolder().findFirstByPredicate(sound -> sound.isSupported(bufferedInputStream));

  3. Play ISound with AudioSource and SoundController (currently only PlayerAudioSource available)

    if(!soundFormat.isPresent())
        // handle unknown sound format
    PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    Player player = //Your player
    int soundDistance = 100; //Maximum distance that will be music played

    PlasmoVoiceSoundPlayer soundPlayer = addon.getPlasmoVoiceSoundPlayer(player.getUniqueId());
    ISound sound = soundFormat.get().newSoundFactory().createSound(inputStream); // This line will automatically close InputStream, after you cannot use stream!
    IPlasmoVoiceSoundController soundController = IPlasmoVoiceSoundController.of(soundFormat.get(), soundDistance));
    soundPlayer.playSound(sound,soundController);

  **How to add custom ISoundFormat (For example Mp3)**
  1. Create class that implements ISoundFormat
            
    public class Mp3SoundFormat implements ISoundFormat {
  
      @Override
      public boolean isSupported(InputStream audioStream) {
          //Check if audio is supported
      }
  
      @Override
      public MusicPlayerSettings getSettings() {
          return new MusicPlayerSettings(sleepDelay,volume,encoderBitrate,soundDistance,shouldUseCaching);
      }
  
      @Override
      public ISoundFactory newSoundFactory() {
          return new Mp3SoundFactory(this);
      }
    }
  2. Create factory that provides pcm data list

    public class Mp3SoundFactory implements ISoundFactory {
      protected final OpusCodecHolder opusCodecHolder = OpusCodecHolder.newCodecHolder();
      protected final ISoundFormat soundFormat;

      public Mp3SoundFactory(ISoundFormat soundFormat) {
          this.soundFormat = soundFormat;
      }
  
      @Override
      public ISound createSound(InputStream audioStream) {
          byte[] yourSoundData = // Create sound data from audioStream, for example with library
          List<byte[]> splittedSoundData = // Split your byte[] opusCodecHolder.getFrameSize()
          List<byte[]> encodedSoundData = new ArrayList<>();
          for(byte[] chunkData : splittedSoundData){
            encodedSoundData.add(opusCodecHolder.encode(chunkData));
          }
          audioStream.close(); // Close stream
          opusCodecHolder.closeEncoder(); // Close encoder, because it uses native library
          return ISound.of(encodedSoundData, soundFormat);
      }
    }   
  3. Add ISoundFormat to the SoundFormatHolder
   
    PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
    addon.getSoundFormatHolder().add(new Mp3SoundFormat()); // Add ISoundFormat to the addon.
  4. Congratulations! You are successfully added custom sound format! 
