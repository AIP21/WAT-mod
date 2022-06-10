<div align=center>

# WAT *(Where are they?)*

[![MIT License](https://img.shields.io/github/license/AIP21/WAT-mod)](LICENSE)
[![FabricMC](https://img.shields.io/badge/mod%20loader-fabric-1976d2)](https://fabricmc.net)
[![FabricMC Tutorial Wiki: Side](https://img.shields.io/badge/environment-client-1976d2)](https://fabricmc.net/wiki/tutorial:side)
[![FabricMC Tutorial Wiki: Side](https://img.shields.io/badge/environment-server-1976d2)](https://fabricmc.net/wiki/tutorial:side)

**WAT** is a mod to track the position of every player connected to a server and then log those positions into a text file. Those logs can then be used with the Player Tracker Decoder app to interactively visualize, analyze, and export that data.

[![CurseForge](https://img.shields.io/badge/curseforge-wat--mod-e96a41?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/wat-mod)
[![GitHub](https://img.shields.io/badge/github-wat--mod-1976d2?logo=github)](https://github.com/AIP21/WAT-mod)
[![Modrinth](https://img.shields.io/badge/modrinth-wat-5da545?logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMSAxMSIgd2lkdGg9IjE0LjY2NyIgaGVpZ2h0PSIxNC42NjciICB4bWxuczp2PSJodHRwczovL3ZlY3RhLmlvL25hbm8iPjxkZWZzPjxjbGlwUGF0aCBpZD0iQSI+PHBhdGggZD0iTTAgMGgxMXYxMUgweiIvPjwvY2xpcFBhdGg+PC9kZWZzPjxnIGNsaXAtcGF0aD0idXJsKCNBKSI+PHBhdGggZD0iTTEuMzA5IDcuODU3YTQuNjQgNC42NCAwIDAgMS0uNDYxLTEuMDYzSDBDLjU5MSA5LjIwNiAyLjc5NiAxMSA1LjQyMiAxMWMxLjk4MSAwIDMuNzIyLTEuMDIgNC43MTEtMi41NTZoMGwtLjc1LS4zNDVjLS44NTQgMS4yNjEtMi4zMSAyLjA5Mi0zLjk2MSAyLjA5MmE0Ljc4IDQuNzggMCAwIDEtMy4wMDUtMS4wNTVsMS44MDktMS40NzQuOTg0Ljg0NyAxLjkwNS0xLjAwM0w4LjE3NCA1LjgybC0uMzg0LS43ODYtMS4xMTYuNjM1LS41MTYuNjk0LS42MjYuMjM2LS44NzMtLjM4N2gwbC0uMjEzLS45MS4zNTUtLjU2Ljc4Ny0uMzcuODQ1LS45NTktLjcwMi0uNTEtMS44NzQuNzEzLTEuMzYyIDEuNjUxLjY0NSAxLjA5OC0xLjgzMSAxLjQ5MnptOS42MTQtMS40NEE1LjQ0IDUuNDQgMCAwIDAgMTEgNS41QzExIDIuNDY0IDguNTAxIDAgNS40MjIgMCAyLjc5NiAwIC41OTEgMS43OTQgMCA0LjIwNmguODQ4QzEuNDE5IDIuMjQ1IDMuMjUyLjgwOSA1LjQyMi44MDljMi42MjYgMCA0Ljc1OCAyLjEwMiA0Ljc1OCA0LjY5MSAwIC4xOS0uMDEyLjM3Ni0uMDM0LjU2bC43NzcuMzU3aDB6IiBmaWxsLXJ1bGU9ImV2ZW5vZGQiIGZpbGw9IiM1ZGE0MjYiLz48L2c+PC9zdmc+)](https://modrinth.com/mod/wat-mod)
  
## Info
  
<div align=left>

**WARNING: DO NOT USE THIS ON SERVERS WITHOUT PERMISSION, YOU COULD GET BANNED!**

**The data created by this mod is effectively useless without using the companion program to visualize the data: [Github page for the companion application](https://github.com/AIP21/TrackerDecoderApp)**

This works on both the client or the server, but it works best on the server. The client version tracks *only* the rendered players, so it has a limited tracking range and only tracks players in the dimension you are in. The server, on the other hand, tracks *all* players in all dimensions.

It is a great way to see what the players on your server are doing and where they are. It allows you to see hotspots of activity and the paths players take to navigate around the world.

The mod lets you configure the rate at which positions are logged, in ticks. You can use either the config file or ModMenu if you use it on your client.

**NOTICE: This mod only tracks connected player names and positions, no other information is collected. I do not collect any data from this mod.**
