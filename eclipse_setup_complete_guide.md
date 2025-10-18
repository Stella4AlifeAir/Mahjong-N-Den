# Complete Eclipse Setup Guide - Run Original Mahjong "N" Den Game

## 🎯 Goal
Run the original 2005 Java mobile game on your Windows PC using Eclipse with a phone emulator.

**What you'll achieve**:
- ✅ Play the original game on your PC
- ✅ Test with 4 AI opponents
- ✅ See authentic 2005 graphics and gameplay
- ✅ Understand the game before rebuilding

---

## ⏱️ Time Required: 1-2 Hours

- Eclipse installation: 15 minutes
- J2ME emulator setup: 30 minutes
- Project import: 15 minutes
- First run: 15 minutes
- Troubleshooting: 30 minutes (if needed)

---

## 📋 PART 1: INSTALL JAVA JDK

### Step 1: Check if Java is Already Installed

**Open Command Prompt**:
1. Press `Win + R`
2. Type `cmd`
3. Press Enter

**Type this command**:
```cmd
java -version
```

**If you see**:
```
java version "1.8.0_xxx" or "11.0.xx" or higher
```
✅ **Java is installed** - Skip to Part 2

**If you see**:
```
'java' is not recognized as an internal or external command
```
❌ **Need to install Java** - Continue below

---

### Step 2: Download Java JDK

**For Windows**:

1. Go to: https://www.oracle.com/java/technologies/downloads/
2. Find **Java 8** (best compatibility with old J2ME)
   - OR use Java 11 (also works well)
3. Click on **Windows** tab
4. Download **x64 Installer** (e.g., `jdk-8u411-windows-x64.exe`)
5. **Save the file** to your Downloads folder

---

### Step 3: Install Java JDK

1. **Double-click** the downloaded installer
2. Click **Next**
3. **Keep default installation folder**:
   ```
   C:\Program Files\Java\jdk1.8.0_411\
   ```
4. Click **Next**
5. Wait for installation (2-3 minutes)
6. Click **Close** when done

---

### Step 4: Verify Java Installation

**Open Command Prompt** (new window):
```cmd
java -version
```

**Should show**:
```
java version "1.8.0_411"
Java(TM) SE Runtime Environment...
```

✅ **Java is ready!**

---

## 📋 PART 2: INSTALL ECLIPSE IDE

### Step 5: Download Eclipse

1. Go to: https://www.eclipse.org/downloads/
2. Click **"Download x86_64"** button
3. Save `eclipse-inst-jee-latest-win.exe` to Downloads
4. Wait for download to complete

---

### Step 6: Install Eclipse

1. **Double-click** `eclipse-inst-jee-latest-win.exe`
2. You'll see **Eclipse Installer** window
3. **Choose**: **"Eclipse IDE for Java Developers"**
   - (NOT "Eclipse IDE for Enterprise Java")
4. **Installation Folder**: Keep default
   ```
   C:\Users\YourUsername\eclipse\java-2024-xx
   ```
5. **JRE**: Should auto-detect Java 8 (if not, click Browse and find it)
6. Click **INSTALL**
7. **Accept licenses** (check "Remember accepted licenses")
8. Wait for installation (5-10 minutes)
9. Click **LAUNCH** when done

---

### Step 7: First Launch Setup

**Welcome Screen**:
1. **Workspace**: Accept default location
   ```
   C:\Users\YourUsername\eclipse-workspace
   ```
2. Click **Launch**
3. **Close the Welcome tab** (X on the tab)

You should now see the **main Eclipse window** with:
- Menu bar at top
- Package Explorer on left (empty)
- Editor area in center
- Console at bottom

✅ **Eclipse is ready!**

---

## 📋 PART 3: INSTALL J2ME (MOBILE EMULATOR)

### Step 8: Install EclipseME Plugin

**The Challenge**: EclipseME is an old plugin (2005) and may not be available in modern Eclipse.

**Option A: Try Eclipse Marketplace** (might work)

1. In Eclipse, go to: **Help** → **Eclipse Marketplace...**
2. Search for: **"J2ME"** or **"EclipseME"**
3. If you find it, click **Install**
4. If NOT found, proceed to Option B

---

**Option B: Manual Installation** (Recommended)

Since EclipseME is discontinued, we'll use an alternative approach:

1. **Download Java ME SDK** (Oracle)
   - Go to: https://www.oracle.com/java/technologies/javame-sdk-downloads.html
   - Download **Java ME SDK 8.3**
   - Install it (default location is fine)

2. **OR use Microemulator** (Simpler alternative)
   - We'll set this up in the next section

---

### Step 9: Download Microemulator (Simple Alternative)

**Microemulator** = Standalone J2ME emulator (easier than EclipseME)

1. Go to: https://github.com/barteo/microemulator/releases
2. Download: **microemulator-2.0.4.zip**
3. **Extract the ZIP** to a folder:
   ```
   C:\microemulator\
   ```
4. You should have:
   ```
   C:\microemulator\microemulator.jar
   C:\microemulator\lib\
   ```

✅ **Emulator downloaded!**

---

## 📋 PART 4: IMPORT THE MAHJONG PROJECT

### Step 10: Prepare the Project Folder

1. Navigate to your downloaded repository:
   ```
   D:\Project\Mahjong Den Development\New Mahjong Den\Mahjong Den ver 001\_backup_mahjongbt\mjbt\
   ```

2. **Copy the entire `mjbt` folder** to a simpler location:
   ```
   C:\MahjongProject\
   ```

   So you have:
   ```
   C:\MahjongProject\mjbt\
   ```

---

### Step 11: Import Project to Eclipse

**In Eclipse**:

1. **File** → **Import...**

2. Expand **General**

3. Select **"Existing Projects into Workspace"**

4. Click **Next**

5. **Select root directory**: Click **Browse...**
   - Navigate to: `C:\MahjongProject\mjbt`
   - Click **Select Folder**

6. The project should appear in the list with a checkbox ✓

7. ✅ **Check**: "Copy projects into workspace"

8. Click **Finish**

---

### Step 12: Wait for Eclipse to Build

Eclipse will automatically start building the project.

**Look at the bottom-right corner** of Eclipse:
- You'll see: "Building workspace..." with a progress bar

**This may take 1-5 minutes.**

When done, you'll see the project in **Package Explorer** on the left:
```
mjbt
  ├── com.pheephoo.mjgame
  ├── com.pheephoo.utilx
  ├── res
  └── JRE System Library
```

---

### Step 13: Check for Errors

**Look at Package Explorer**:
- ❌ **Red X icons** = Errors (need to fix)
- ⚠️ **Yellow ! icons** = Warnings (can ignore)

**Common errors**:
1. **"javax.microedition cannot be resolved"** - Missing J2ME libraries
2. **"JRE System Library unbound"** - Wrong Java version

---

### Step 14: Add J2ME Libraries (If Errors)

**If you see import errors**:

1. **Right-click** on the project `mjbt`
2. **Properties** → **Java Build Path**
3. Click **Libraries** tab
4. Click **Add External JARs...**
5. Navigate to:
   ```
   C:\microemulator\lib\
   ```
6. Select **ALL .jar files**
7. Click **Open**
8. Click **Apply and Close**

The project should rebuild automatically.

---

## 📋 PART 5: CONFIGURE AND RUN

### Step 15: Find the Main Class

**In Package Explorer**, expand:
```
mjbt → com.pheephoo.mjgame → MJGame.java
```

**Double-click** `MJGame.java` to open it.

You should see the source code in the editor.

---

### Step 16: Create Run Configuration

**Method A: Simple Run** (Try this first)

1. **Right-click** on `MJGame.java` in Package Explorer
2. **Run As** → **Java Application**

**If you see an error** about "Selection does not contain a main type", proceed to Method B.

---

**Method B: Manual Configuration**

1. **Run** → **Run Configurations...**

2. **Double-click** "Java Application" in the left list
   - This creates a new configuration

3. **Name**: Type `Mahjong Game`

4. **Project**: Click **Browse...** → Select `mjbt`

5. **Main class**: Click **Search...** → Select `MJGame`

6. **Arguments** tab:
   - VM arguments: (leave empty for now)

7. **Classpath** tab:
   - Make sure `mjbt` project is listed
   - Make sure J2ME libraries are listed

8. Click **Apply**

9. Click **Run**

---

### Step 17: First Run - Expect Errors!

**You'll likely see errors because**:
- J2ME MIDlet needs special runtime
- Database connection needs setup
- Network server is offline

**Common errors you'll see**:
```
javax.microedition.midlet.MIDletStateChangeException
```
OR
```
ClassNotFoundException: javax.microedition.lcdui.Display
```

This is **NORMAL**! We need the emulator.

---

## 📋 PART 6: RUN WITH MICROEMULATOR

### Step 18: Run Using Microemulator

**Open Command Prompt**:

```cmd
cd C:\microemulator
java -jar microemulator.jar
```

**Microemulator window opens** (looks like a phone screen!)

---

### Step 19: Load the Game

**In Microemulator window**:

1. **File** → **Open MIDlet File...**

2. Navigate to:
   ```
   C:\MahjongProject\mjbt\deployed\mjbt.jar
   ```

3. Click **Open**

4. **Select**: `BT Demo` (the MIDlet)

5. Click **Start**

---

### Step 20: Play the Game!

**If successful, you should see**:
1. **Splash screen** (Mahjong logo)
2. **Main menu** with options:
   - Play
   - Private Network
   - Public Network
   - Settings
   - About
   - Exit

3. **Click "Play"** → Game starts with 3 AI opponents!

🎉 **YOU'RE PLAYING THE ORIGINAL 2005 GAME!**

---

## 🎮 GAME CONTROLS IN EMULATOR

### Microemulator Controls:

**Mouse**:
- Click on the phone screen to interact
- Click buttons

**Keyboard**:
- **Arrow keys** → Navigate
- **Enter** → Select/OK
- **Backspace** → Back
- **0-9** → Number keys
- **\*** → Star key
- **#** → Hash key

---

## 🔧 TROUBLESHOOTING

### Issue 1: "Cannot find mjbt.jar"

**Solution**: Build the JAR file

1. In Eclipse, right-click project
2. **Export** → **JAR file**
3. Select `mjbt` project
4. Export destination: `C:\MahjongProject\mjbt\deployed\mjbt.jar`
5. Click **Finish**
6. Try loading in Microemulator again

---

### Issue 2: "Error loading MIDlet"

**Solution**: Check the JAD file

1. Open `mjbt.jad` in Notepad
2. Make sure this line points to the correct JAR:
   ```
   MIDlet-Jar-URL: mjbt.jar
   ```
3. Make sure JAR and JAD are in same folder

---

### Issue 3: Game tries to connect to network and fails

**Solution**: Play offline mode

1. In main menu, click **"Play"** (not "Private Network" or "Public Network")
2. This starts offline mode with AI opponents
3. No network needed!

---

### Issue 4: Black screen or crash

**Possible causes**:
- Missing resources (images/sounds)
- Wrong Java version
- Corrupted JAR file

**Solutions**:
1. Re-extract the ZIP from GitHub
2. Make sure `res` folder is included in JAR
3. Try rebuilding the project

---

### Issue 5: Can't see tiles/images

**Solution**: Make sure resources are in JAR

**Check JAR contents**:
1. Copy `mjbt.jar` to Desktop
2. Rename to `mjbt.zip`
3. Extract it
4. Look for `res` folder with PNG files
5. If missing, need to rebuild with resources

---

## 🎯 ALTERNATIVE: USE ECLIPSE DEBUGGING

### Method: Run as Console Application (No Graphics)

If emulator doesn't work, you can still test the game logic:

**Create a test class**:

```java
public class TestMahjong {
    public static void main(String[] args) {
        // Test tile creation
        Tile tile = new Tile(Tile.CHARACTER, 5);
        System.out.println("Created tile: " + tile);
        
        // Test deck shuffling
        MahjongDeck deck = new MahjongDeck();
        deck.shuffle();
        System.out.println("Deck shuffled with " + deck.size() + " tiles");
        
        // Test scoring
        WinEngine winEngine = new WinEngine();
        // ... add test code
    }
}
```

**Run this** to test core game logic without UI.

---

## 📱 ALTERNATIVE EMULATORS

If Microemulator doesn't work well, try these:

### 1. **KEmulator**
- Download: http://www.kemulator.com/
- Better graphics
- Faster performance
- Windows only

### 2. **Nokia SDK** (if you can find it)
- Official Nokia emulator
- Most accurate
- Hard to find nowadays

### 3. **J2ME Loader** (Android)
- Install on Android phone
- Transfer mjbt.jar to phone
- Run natively on mobile device
- Best experience!

---

## ✅ SUCCESS CHECKLIST

After completing this guide, you should have:

- [x] Java JDK installed
- [x] Eclipse IDE installed and working
- [x] Project imported into Eclipse
- [x] Microemulator installed
- [x] Game JAR file loaded
- [x] Can see main menu
- [x] Can play against AI opponents
- [x] Can see tiles and UI
- [x] Understand the original game

---

## 🎮 WHAT TO DO ONCE GAME RUNS

### Explore the Game:

1. **Play a full game** against AI
   - Understand the flow
   - Learn the controls
   - See scoring in action

2. **Try different settings**
   - Sound on/off
   - Speed settings
   - Difficulty levels

3. **Take notes**:
   - What works well?
   - What feels dated?
   - What to improve in modern version?

4. **Take screenshots**:
   - Document the UI
   - Capture gameplay moments
   - Reference for redesign

---

## 🚀 NEXT STEPS AFTER TESTING

### Understanding the Code:

1. **Read through MJGame.java**
   - Understand application lifecycle
   - See how screens transition

2. **Study WinEngine.java**
   - Understand scoring calculations
   - Learn Mahjong rules

3. **Examine Series60Client.java**
   - See how tiles are rendered
   - Understand game canvas

4. **Review NetworkCommand.java**
   - Understand multiplayer protocol
   - See message types

### Planning the Rebuild:

1. **List improvements needed**:
   - Modern graphics (tiles at 4K)
   - Touch-friendly UI
   - Cloud multiplayer
   - Social features

2. **Design mockups**:
   - Sketch new UI layouts
   - Plan navigation flow
   - Design tile style

3. **Choose tech stack**:
   - Flutter (recommended)
   - React Native
   - Unity

---

## 💡 PRO TIPS

### Tip 1: Run in Debug Mode
```
Right-click MJGame.java → Debug As → Java Application
```
Set breakpoints to understand code flow.

### Tip 2: View Console Output
```
Window → Show View → Console
```
See game logs and debug messages.

### Tip 3: Modify Constants
In `Constant.java`, change:
```java
public static int N_USERWAITTIME = 20; // Change to 60 for slower gameplay
```
Recompile and test.

### Tip 4: Test Offline Mode
The offline mode (vs AI) doesn't need:
- Database connection
- Network server
- SMS registration

Perfect for testing!

---

## 🆘 STILL STUCK?

**If you can't get it running**, tell me:

1. **What step are you on?**
2. **What error message do you see?**
3. **Screenshot the error** (if possible)
4. **Which method are you trying?** (Eclipse, Microemulator, etc.)

I'll help you troubleshoot step-by-step!

---

## 🎉 CONGRATULATIONS!

Once you get the game running, you'll have:
- ✅ Experienced the original 2005 gameplay
- ✅ Understood the code structure
- ✅ Tested Cantonese Mahjong rules
- ✅ Seen what needs modernizing
- ✅ Ready to plan your rebuild!

**You're now ready to create the modern version!** 🚀

---

## 📞 QUICK REFERENCE

### Key File Locations:
```
Project: C:\MahjongProject\mjbt\
JAR: C:\MahjongProject\mjbt\deployed\mjbt.jar
JAD: C:\MahjongProject\mjbt\deployed\mjbt.jad
Main Class: com.pheephoo.mjgame.MJGame
Resources: res/ folder (tiles, sounds)
```

### Key Commands:
```cmd
# Check Java
java -version

# Run Microemulator
cd C:\microemulator
java -jar microemulator.jar

# Build JAR (if needed)
# In Eclipse: Export → JAR file
```

### Key Controls:
```
Arrow keys = Navigate
Enter = Select
Backspace = Back
Mouse click = Touch
```

**Good luck!** 🎮