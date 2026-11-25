package consumer

import probe.MechanismProbe

// This declaration must compile against published common metadata, not only platform classes.
fun createProbe(seed: Long): MechanismProbe = MechanismProbe(seed)
