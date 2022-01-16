![Lines of code](https://img.shields.io/tokei/lines/github/U61vashka/PlasmoVoiceAddon?label=lines%20of%20code) 
[![Jitpack](https://jitpack.io/v/U61vashka/PlasmoVoiceAddon.svg)](https://jitpack.io/#U61vashka/PlasmoVoiceAddon)
[![Github Releases Downloads](https://img.shields.io/github/downloads/U61vashka/PlasmoVoiceAddon/total?label=downloads%20%5Bgithub%20releases%5D)](https://github.com/U61vashka/PlasmoVoiceAddon/releases)

PlasmoVoiceAddon is a music player (Supports mp3 and wav) from url or local file. And provides api for playing music. 

> Important! This is plugin depends on plugin [PlasmoVoice](https://github.com/plasmoapp/plasmo-voice). And you cannot play music for the player that not downloaded mod [PlasmoVoice](https://github.com/plasmoapp/plasmo-voice)

# How to install plugin:
  1. Download jar from spigot or github releases
  2. Place in your spigot plugins folder
  3. Launch server

# For Developers:
  > Important! Do not play music on main thread! Because it will crash server!


**How to use API?**

  - Get ISound from WAV format file:

       ```
        // First we need to get AudioInputStream from our audio source
  
        File file = //Your wav sound file
          
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
      
        // Then we get Sound from AudioInputStream

        PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
        ISound sound = new AudioStreamSound(audioInputStream,addon.getPlasmoVoiceSoundPlayer().getCodecHolder(),true);
        // Now we have ISound but we need to play it somewhere 
      ```
  - Get ISound from WAV format URL
      ```
        URL url = //Your wav sound file url

        // We will open stream from url
        InputStream urlInputStream = url.openStream();

        //Then convert it to BufferedInputStream
        BufferedInputStream bufferedInputStream = new BufferedInputStream(urlInputStream);

        //Get AudioInputStream from our bufferedInputStream
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

        //Then convert AudioInputStream to ISound
        PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
        ISound sound = new AudioStreamSound(audioInputStream,
                 addon.getPlasmoVoiceSoundPlayer().getCodecHolder(), true);
      ``` 
  - Get ISound from MP3 format file
      ```
        File file = //Your mp3 file

        //We create InputStream like WAV file. But we will use MP3Sound
        ISound sound = new MP3Sound(new BufferedInputStream(Files.newInputStream(file.toPath())),
								PLUGIN.getPlasmoVoiceSoundPlayer());
      ```
  - Get ISound from MP3 format url
      ```
         URL url = //Your mp3 sound file url
         
         // We will open stream from url
         InputStream urlInputStream = url.openStream();

         //Then convert it to BufferedInputStream
         BufferedInputStream bufferedInputStream = new BufferedInputStream(urlInputStream);

         //Get AudioInputStream from our bufferedInputStream
         ISound sound = new MP3Sound(bis, PLUGIN.getPlasmoVoiceSoundPlayer());
      ```

**How to play ISound?**
   After you have converter your file or url to ISound you will need to play sound:
      ```
        PlasmoVoiceAddon addon = PlasmoVoiceAddon.getPlugin(PlasmoVoiceAddon.class);
        ISound sound = //Our iSound
        
        //Currently only Player SoundSource is supported
        Player player = //
        PlayerAudioSource playerAudioSource = new PlayerAudioSource(player.getUniqueId(),
								PLUGIN.getPlasmoVoiceSoundPlayer());
        
        int playDistance = 100;
        //Play your sound with specific distance
        playerAudioSource.sendAudioData(sound, playDistance);
      ```
    
          

        
     
