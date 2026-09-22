# The Ionic Live Update Provider SDK is a compileOnly dependency: it is only
# present at runtime when the app also uses Federated Capacitor or Ionic
# Portals (which bring it transitively). Suppress R8 missing-class errors for
# apps that don't.
-dontwarn io.ionic.liveupdateprovider.**
