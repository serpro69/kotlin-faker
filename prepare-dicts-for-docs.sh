for i in core/src/main/resources/locales/en/*.yml; do
  # IF first line does not already have a comment
  n="provider_${i%[.]*}"
  sed -i -e '1i# START '"$n"'' -e '$a# END '"$n"'' -- "$i"
done
