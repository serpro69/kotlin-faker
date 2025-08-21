#!/usr/bin/env perl

use strict;
use warnings;
use File::Basename;

# 1. Expect a single file path as a command-line argument.
my $filepath = shift @ARGV;
die "Usage: $0 <path/to/file.adoc>\n" unless defined $filepath && -f $filepath;

# 2. Extract the basename of the file, stripping the '.adoc' extension.
my $file_basename = basename($filepath, ".adoc");

# 3. Read the entire contents of the specified file.
my $content = do {
    open my $fh, '<', $filepath or die "Could not open file '$filepath': $!";
    local $/;
    <$fh>;
};

# 4. Extract the provider name from the content for use in the title.
my ($provider_name) = $content =~ /`Faker\(\)\.([a-zA-Z_]+)`/;
die "Could not find a provider name like 'Faker().provider' in '$filepath'" unless $provider_name;
#my $title = ucfirst($provider_name);
my $title = $provider_name;

# --- Begin Transformations ---

# Replace the empty frontmatter with one containing the derived title.
$content =~ s/^---\n---\n/---\ntitle: $title\n---\n/;

# Convert the AsciiDoc H2 heading to Markdown H2.
$content =~ s/^== /## /m;

# Transform the "Dictionary file" block.
$content =~ s{
    \.Dictionary\sfile\n
    \[%collapsible\]\n
    ====\n
    \[source,yaml\]\n
    ----\n
    \{%\s*snippet\s*'([^']*)'\s*%\}
    \s*\n----\n
    ====
}
{
    my $snippet_name = $1;
    # Use the file's basename to construct the new include path.
    "??? example \"dictionary file\"\n" .
    "    ```yaml\n" .
    "    --8<-- \"core/src/main/resources/locales/en/$file_basename.yml:$snippet_name\"\n" .
    "    ```"
}msxge;

# Transform the "Available Functions" block.
$content =~ s{
    \.Available\sFunctions\n
    \[%collapsible\]\n
    ====\n
    \[source,(\w+)\]\n
    ----\n
    (.*?)\n
    ----\n
    ====
}
{
    my $lang = $1;
    my $code = $2;
    $code =~ s/^\s+|\s+$//g; # Trim leading/trailing whitespace.
    $code =~ s/^/    /mg;     # Indent each line of the code.
    "???+ example \"available functions\"\n" .
    "    ```$lang\n" .
    "$code\n" .
    "    ```"
}msxge;

# Print the final, transformed content to standard output.
print $content;
