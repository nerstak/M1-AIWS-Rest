{
    description = "M1-AIWS-REST";
    inputs = {
        unstable.url = "github:NixOS/nixpkgs/nixos-unstable";
    };
    outputs = inputs:
        let 
            system = "x86_64-linux";
            pkgs = inputs.unstable.legacyPackages.${system};
        in {
            devShell."${system}" = pkgs.mkShell {
                buildInputs = with pkgs; [
                    elmPackages.elm
                    elmPackages.elm-format
                    elmPackages.elm-instrument
                    elmPackages.elm-json
                    elmPackages.elm-analyse
                    elmPackages.elm-coverage
                    elmPackages.elm-doc-preview
                    elmPackages.elm-live
                    elmPackages.elm-optimize-level-2
                    elmPackages.elm-review
                    elmPackages.elm-test
                    elmPackages.elm-upgrade
                    elmPackages.elm-verify-examples
                    elmPackages.elm-xref
                    elmPackages.elm-language-server
                    openjdk8
                    nodejs_latest
                ];
            };
        };
}
