{
  inputs = {
    nixpkgs = {
      url = "github:nixos/nixpkgs/nixos-unstable";
    };
  };

  outputs =
    { nixpkgs, ... }:
    let
      jdk = pkgs.jdk25;
      pkgs = import nixpkgs { inherit system; };
      system = "x86_64-linux";
    in
    with pkgs;
    {
      devShells."${system}".default = mkShell {
        buildInputs = [
          coursier
          jdk
          ((mill.override { jre = jdk; }).overrideAttrs rec {
            version = "1.1.0-RC1";

            src = pkgs.fetchurl {
              url = "https://repo1.maven.org/maven2/com/lihaoyi/mill-dist-native-linux-amd64/${version}/mill-dist-native-linux-amd64-${version}.exe";
              hash = "sha256-i8omvQ/mzKdPX4D1aUpxi1wI8fZiyTdJlE/qMqszsUE=";
            };
          })
          scalafmt
          xorg.xrandr
        ];

        LD_LIBRARY_PATH = lib.makeLibraryPath [
          libGL
          openal
          xorg.libXxf86vm
        ];
      };
    };
}
