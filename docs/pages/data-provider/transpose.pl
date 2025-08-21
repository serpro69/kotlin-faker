#!/usr/bin/env perl

use strict;
use warnings;

# Read all of STDIN into a single string
my $content = do { local $/; <> };

# Extract the provider name (e.g., "bible") from the heading.
# This is the key to making the script generic for other files.
my ($provider) = $content =~ /`Faker\(\)\.([a-z_]+)`/;
die "Could not find provider name in input" unless $provider;
my $title = ucfirst($provider);

# 1. Replace the YAML frontmatter
$content =~ s/^---\n---\n/---\ntitle: $title\n---\n/;

# 2. Replace the main heading
$content =~ s/^== /## /m;

# 3. Replace the "Dictionary file" block
$content =~ s{
    \.Dictionary\sfile\n          # Block title
    \[%collapsible\]\n            # Collapsible attribute
    ====\n                        # Block delimiter
    \[source,yaml\]\n             # Language attribute
    ----\n                        # Code block delimiter
    \{%\s*snippet\s*'([^']*)'\s*%\} # The snippet tag and its name
    \s*\n----\n                   # Code block delimiter
    ====                          # Block delimiter
}
{
    my $snippet_name = $1;
    # Construct the new admonition block
    "??? example \"dictionary file\"\n" .
    "    ```yaml\n" .
    "    --8<-- \"core/src/main/resources/locales/en/$provider.yml:$snippet_name\"\n" .
    "    ```"
}msxge;

# 4. Replace the "Available Functions" block
$content =~ s{
    \.Available\sFunctions\n      # Block title
    \[%collapsible\]\n            # Collapsible attribute
    ====\n                        # Block delimiter
    \[source,(\w+)\]\n            # Language attribute (e.g., kotlin)
    ----\n                        # Code block delimiter
    (.*?)\n                       # The code content itself
    ----\n                        # Code block delimiter
    ====                          # Block delimiter
}
{
    my $lang = $1;
    my $code = $2;

    # Clean up whitespace from the captured code
    $code =~ s/^\s+//s;
    $code =~ s/\s+$//s;

    # Indent every line of the code block by 4 spaces for the admonition
    $code =~ s/^/    /mg;

    # Construct the new admonition block
    "???+ example \"available functions\"\n" .
    "    ```$lang\n" .
    "$code\n" .
    "    ```"
}msxge;

# Print the final transformed content to STDOUT
print $content;
