#!/bin/bash
######

_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
if [[ "${_dir}" != "$(pwd)" ]]; then
    cd "${_dir}" || exit 1
fi

cd ./core/src/main/resources/locales/en || exit 1

for i in ./*.yml; do
  s="${i:2}"
  n="${s%[.]*}_provider_dict"
  if head -n 1 "${i}" | grep -qe "^# START $n" && tail -n 1 "${i}" | grep -qe "^# END $n" ; then
    echo "nothing to do" >/dev/null 2>&1
  elif head -n 1 "${i}" | grep -qe "^# START $n" ; then
    echo "${i} does not match code snippet pattern on last line"
  elif tail -n 1 "${i}" | grep -qe "^# END $n" ; then
    echo "${i} does not match code snippet pattern on first line"
  else
    echo "modifying ${i}"
    sed -i -e '1i# START '"$n"'' -e '$a# END '"$n"'' -- "$i"
  fi
done

cd "${_dir}" || exit 1
