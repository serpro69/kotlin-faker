import re


def define_env(env):
    """
    This is the hook for defining variables, macros and filters

    - variables: the dictionary that contains the environment variables
    - macro: a decorator function, to declare a macro.
    - filter: a decorator function, to declare a filter.
    """

    @env.filter
    def regex_replace(s, find, replace):
        """A non-jinja default re.sub()."""
        return re.sub(find, replace, s)


def on_pre_page_macros(env):
    """
    Actions to be done before macro interpretation,
    just before the markdown is generated
    """
    # base URI of the page (with versioning support)
    env.page.meta['base_uri'] = re.sub(
        env.page.file.page.url, '', env.page.canonical_url)


def on_post_page_macros(env):
    """
    Actions to be done after macro interpretation,
    just before the markdown is generated
    """
    # base URI of the page (with versioning support)
    env.page.meta['base_uri'] = re.sub(
        env.page.file.page.url, '', env.page.canonical_url)
