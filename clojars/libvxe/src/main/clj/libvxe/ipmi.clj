(ns libvxe.ipmi)

(defn ipmi-version-type []
  (let [crc (IpmiSessionManager.)
        session (.newIpmiSession crc)
        auth-type (keyword (.toString (.getAuthenticationType session)))
        version-map {:MD2
                     "This means IPMI v1.5"
                     :MD5
                     "This means IPMI v1.5"
                     :NONE
                     "This means IPMI v1.5"
                     :OEM_PROPRIETARY "This means IPMI v1.5"

                     :PASSWORD "This means IPMI v1.5"

                     :RMCPP "This means IPMI v2.0"
                     }
        ]
    (auth-type version-map)))

